package com.yasminagbreil.metro.di

import com.yasminagbreil.metro.data.FakeMetroRepository
import com.yasminagbreil.metro.domain.MetroRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindMetroRepository(
        impl: FakeMetroRepository
    ): MetroRepository
}