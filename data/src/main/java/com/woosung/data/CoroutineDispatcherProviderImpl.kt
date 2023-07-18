package com.woosung.data

import com.woosung.domain.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * 테스트 코드 작성하려다가 못했다.
 *
 * @constructor Create empty Coroutine dispatcher provider impl
 */
internal class CoroutineDispatcherProviderImpl : CoroutineDispatcherProvider {
    override val dispatcherDefault: CoroutineDispatcher
        get() = Dispatchers.Default
    override val dispatcherIO: CoroutineDispatcher
        get() = Dispatchers.IO
    override val dispatcherMain: CoroutineDispatcher
        get() = Dispatchers.Main
}
