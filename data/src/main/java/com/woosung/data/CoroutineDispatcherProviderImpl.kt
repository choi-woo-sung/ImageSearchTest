package com.kts6056.data

import com.woosung.domain.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class CoroutineDispatcherProviderImpl : CoroutineDispatcherProvider {
    override val dispatcherDefault: CoroutineDispatcher
        get() = Dispatchers.Default
    override val dispatcherIO: CoroutineDispatcher
        get() = Dispatchers.IO
    override val dispatcherMain: CoroutineDispatcher
        get() = Dispatchers.Main
}
