package com.krolikowski.timerpomodoro.ui.pomodorolist

import androidx.lifecycle.ViewModel
import com.krolikowski.timerpomodoro.di.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PomodoroListModule {

    @ContributesAndroidInjector
    abstract fun contributePomodoroListFragment(): PomodoroListFragment

    @Binds
    @IntoMap
    @ViewModelKey(PomodoroListViewModel::class)
    abstract fun bindPomodoroListViewModel(viewModel: PomodoroListViewModel): ViewModel
}