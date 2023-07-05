package com.woosung.data.di

import com.woosung.data.remote.SearchAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {
    @Provides
    @Singleton
    fun provideSearchService(
        retrofit: Retrofit,
    ): SearchAPI = retrofit.create(SearchAPI::class.java)
}
