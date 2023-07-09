package com.woosung.data.di

import com.woosung.data.local.SearchSharedPreference
import com.woosung.data.local.SearchSharedPreferenceImpl
import com.woosung.data.remote.SearchRemoteDataSource
import com.woosung.data.remote.SearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {


    @Binds
    @Singleton
    abstract fun bindSearchDataSource(searchDataSourceImpl: SearchRemoteDataSourceImpl): SearchRemoteDataSource


    @Binds
    @Singleton
    abstract fun bindSearchPreferenceDataSource(searchDataSourceImpl: SearchSharedPreferenceImpl): SearchSharedPreference
}
