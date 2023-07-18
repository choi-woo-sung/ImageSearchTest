package com.woosung.domain

import kotlinx.coroutines.CoroutineDispatcher

/**
 * 코루틴 디스패처를 주입하려다가 안한이유는?
 *
 */
interface CoroutineDispatcherProvider {
    val dispatcherDefault: CoroutineDispatcher
    val dispatcherIO: CoroutineDispatcher
    val dispatcherMain: CoroutineDispatcher
}
