package com.tbc.bookli.di

import com.tbc.bookli.data.local.repository.CacheRepositoryImpl
import com.tbc.bookli.data.remote.repository.BookRepositoryImpl
import com.tbc.bookli.data.remote.repository.LocationRepositoryImpl
import com.tbc.bookli.data.remote.repository.LoginRepositoryImpl
import com.tbc.bookli.data.remote.repository.RegisterRepositoryImpl
import com.tbc.bookli.data.remote.repository.UserRepositoryImpl
import com.tbc.bookli.domain.repository.BookRepository
import com.tbc.bookli.domain.repository.CacheRepository
import com.tbc.bookli.domain.repository.LocationRepository
import com.tbc.bookli.domain.repository.LoginRepository
import com.tbc.bookli.domain.repository.RegisterRepository
import com.tbc.bookli.domain.repository.UserRepository
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
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}
