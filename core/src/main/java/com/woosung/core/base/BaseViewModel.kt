package com.woosung.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosung.domain.DomainModel
import com.woosung.domain.Result
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {
    private val _jobs: MutableMap<String, Job> = mutableMapOf()

    private val _errorHandler = CoroutineExceptionHandler { _, exception ->
        handleException(exception)
    }

    /**
     * Coroutine Task 실행하며 감지되지 않은 예외케이스를 처리 합니다.
     *
     * @param 감지되지 않은 예외
     */
    protected open fun handleException(exception: Throwable) {
        Timber.e(exception)
    }

    /**
     * Coroutine Job 을 생성합니다.
     *
     * @param context       Coroutine Context
     * @param start         Coroutine 시작 정보
     * @param errorHandler  Coroutine 에러 핸들러
     * @param block         Coroutine에서 실행될 Task
     * @return Coroutine Job
     */
    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        errorHandler: CoroutineExceptionHandler = _errorHandler,
        block: suspend CoroutineScope.() -> Unit
    ): Job = (viewModelScope + errorHandler).launch(context, start, block)

    /**
     * Key 기준으로 Coroutine Job을 생성하고, 제일 마지막으로 제일 마지막 Coroutine만 실행합니다.
     *
     * @param key           중복 실행을 방지하기 위한 Key
     * @param context       Coroutine Context
     * @param start         Coroutine 시작 정보
     * @param errorHandler  Coroutine 에러 핸들러
     * @param block         Coroutine에서 실행될 Task
     * @return
     */
    protected fun launchLatest(
        key: String,
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        errorHandler: CoroutineExceptionHandler = _errorHandler,
        block: suspend CoroutineScope.() -> Unit
    ): Job = launch(context, start, errorHandler) {
        cancelLaunchLatestJob(key)
        launch(context, start, errorHandler, block).also {
            _jobs[key] = it
        }.join()
    }

    /**
     * ViewModel에서 관리하고 있는 Job Pool에서 Key를 기준으로 Job을 cancleAndJoin() 실행합니다.
     *
     * @param key   중복 실행을 방지하기 위한 Key
     * @return Key를 기준으로 Job을 취소요청후 취소 될때까지 기다립니다.
     */
    protected suspend fun cancelLaunchLatestJob(key: String) = _jobs[key]?.cancelAndJoin()

    protected fun <T> async(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T
    ): Deferred<T> = viewModelScope.async(context, start, block)

    /**
     * @param doOnComplete 성공여부 상관 없이 onOnError, doOnSuccess 호출 후 맨 마지막으로 실행 됩니다.
     * @param doOnError 실패시 실행 됩니다.
     * @param doOnSuccess 성공시 실행 됩니다.
     *
     * Result 값을 기준으로 doOnError, doOnSuccess, doOnComplete 호출 됩니다.
     * Result 값이 성공인 경우 doOnSuccess -> doOnComplete 순서대로 호출 됩니다.
     * Result 값이 실패인 경우 doOnError -> doOnComplete 순서대로 호출 됩니다.
     */
    protected fun <T : DomainModel> Result<T>.onComplete(
        doOnComplete: () -> Unit = {},
        doOnError: (exception: Throwable) -> Unit = ::handleException,
        doOnSuccess: T.() -> Unit
    ) {
        when (this) {
            is Result.Failure -> doOnError(exception)
            is Result.Success -> doOnSuccess(data)
        }
        doOnComplete()
    }

    /**
     * @param doOnComplete 성공여부 상관 없이 onOnError, doOnSuccess 호출 후 맨 마지막으로 실행 됩니다.
     * @param doOnError 실패시 실행 됩니다.
     * @param doOnSuccess 성공시 실행 됩니다.
     *
     * Result 값을 기준으로 doOnError, doOnSuccess, doOnComplete 호출 됩니다.
     * Result 값이 성공인 경우 doOnSuccess -> doOnComplete 순서대로 호출 됩니다.
     * Result 값이 실패인 경우 doOnError -> doOnComplete 순서대로 호출 됩니다.
     */
    protected suspend fun <T : DomainModel> Result<T>.onCompleteSuspend(
        doOnComplete: suspend () -> Unit = {},
        doOnError: suspend (exception: Throwable) -> Unit = { handleException(it) },
        doOnSuccess: suspend T.() -> Unit
    ) {
        when (this) {
            is Result.Failure -> doOnError(exception)
            is Result.Success -> doOnSuccess(data)
        }
        doOnComplete()
    }
}
