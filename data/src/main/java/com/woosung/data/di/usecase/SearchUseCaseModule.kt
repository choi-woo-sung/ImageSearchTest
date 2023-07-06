package com.woosung.data.di.usecase

import com.woosung.domain.repository.SearchRepository
import com.woosung.domain.usecase.GetImageForPagingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object SearchUseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideGetImageForPagingUseCase(
        searchRepository: SearchRepository
    ) = GetImageForPagingUseCase(
        searchRepository
    )
}
