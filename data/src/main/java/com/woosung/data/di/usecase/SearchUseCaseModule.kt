//package com.woosung.data.di.usecase
//
//import com.kts6056.domain.CoroutineDispatcherProvider
//import com.woosung.domain.repository.SearchRepository
//import com.kts6056.domain.usecase.GetUserUseCase
//import com.kts6056.domain.usecase.SearchUserUseCase
//import com.kts6056.domain.usecase.UpdateUserBookmarkUseCase
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ViewModelComponent
//import dagger.hilt.android.scopes.ViewModelScoped
//
//@Module
//@InstallIn(ViewModelComponent::class)
//internal object SearchUseCaseModule {
//    @Provides
//    @ViewModelScoped
//    fun provideSearchUserUseCase(
//        coroutineDispatcherProvider: CoroutineDispatcherProvider,
//        searchRepository: SearchRepository
//    ) = SearchUserUseCase(
//        coroutineDispatcherProvider,
//        searchRepository
//    )
//
//    @Provides
//    @ViewModelScoped
//    fun provideUpdateUserBookmarkUseCase(
//        coroutineDispatcherProvider: CoroutineDispatcherProvider,
//        searchRepository: SearchRepository
//    ) = UpdateUserBookmarkUseCase(
//        coroutineDispatcherProvider,
//        searchRepository
//    )
//
//    @Provides
//    @ViewModelScoped
//    fun provideGetUserUseCase(
//        coroutineDispatcherProvider: CoroutineDispatcherProvider,
//        searchRepository: SearchRepository
//    ) = GetUserUseCase(
//        coroutineDispatcherProvider,
//        searchRepository
//    )
//}
