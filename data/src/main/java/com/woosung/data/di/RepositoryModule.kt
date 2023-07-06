package com.woosung.data.di

import com.woosung.data.repository.SearchRepositoryImpl
import com.woosung.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository
}
