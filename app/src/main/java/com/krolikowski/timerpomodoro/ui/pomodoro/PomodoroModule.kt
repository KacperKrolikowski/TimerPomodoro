package com.krolikowski.timerpomodoro.ui.pomodoro

import androidx.lifecycle.ViewModel
import com.krolikowski.timerpomodoro.di.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PomodoroModule {

    @ContributesAndroidInjector
    abstract fun contributePomodoroFragment(): PomodoroFragment

    @Binds
    @IntoMap
    @ViewModelKey(PomodoroViewModel::class)
    abstract fun bindPomodoroViewModel(viewModel: PomodoroViewModel): ViewModel
}