package com.example.android_bootcamp.di

import com.example.android_bootcamp.data.local.repository.CacheRepositoryImpl
import com.example.android_bootcamp.data.remote.repository.BookRepositoryImpl
import com.example.android_bootcamp.data.remote.repository.LocationRepositoryImpl
import com.example.android_bootcamp.data.remote.repository.LoginRepositoryImpl
import com.example.android_bootcamp.data.remote.repository.RegisterRepositoryImpl
import com.example.android_bootcamp.domain.repository.BookRepository
import com.example.android_bootcamp.domain.repository.CacheRepository
import com.example.android_bootcamp.domain.repository.LocationRepository
import com.example.android_bootcamp.domain.repository.LoginRepository
import com.example.android_bootcamp.domain.repository.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl): RegisterRepository

    @Binds
    @Singleton
    abstract fun bindCacheRepository(cacheRepositoryImpl: CacheRepositoryImpl): CacheRepository

    @Binds
    @Singleton
    abstract fun bindBookRepository(bookRepositoryImpl: BookRepositoryImpl): BookRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository
}
