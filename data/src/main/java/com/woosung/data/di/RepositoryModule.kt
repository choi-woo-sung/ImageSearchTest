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

    /**
     *  provide -> 근본적으로 library 코드,저희손으로 어떻게 할수없으니 객체선언하고 주입할때에 가정(손을 떠남)
     *  binds -> 내범주안에 있음, 인터페이스를 직접구현안한게 있을때
     * 싱글톤 어노테이션을 안붙이면 새로 생성된다~
     * Dagger를 해봤냐?
     *
     * @param searchRepositoryImpl
     * @return
     */
    @Binds
    @Singleton
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository
}
