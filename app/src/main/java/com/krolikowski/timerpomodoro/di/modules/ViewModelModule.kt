package com.krolikowski.timerpomodoro.di.modules

import androidx.lifecycle.ViewModelProvider
import com.krolikowski.timerpomodoro.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}