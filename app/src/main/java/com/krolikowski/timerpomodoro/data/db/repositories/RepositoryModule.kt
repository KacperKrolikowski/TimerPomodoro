package com.krolikowski.timerpomodoro.data.db.repositories

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providePomodoroDatabaseRepository(pomodoroDatabaseRepository: PomodoroDatabaseRepository) : PomodoroDatabaseRepositoryType
}