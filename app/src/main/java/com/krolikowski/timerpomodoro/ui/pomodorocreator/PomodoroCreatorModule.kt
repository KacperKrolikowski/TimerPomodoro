package com.krolikowski.timerpomodoro.ui.pomodorocreator

import androidx.lifecycle.ViewModel
import com.krolikowski.timerpomodoro.di.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PomodoroCreatorModule {

    @ContributesAndroidInjector
    abstract fun contributePomodoroCreatorFragment(): PomodoroCreatorFragment

    @Binds
    @IntoMap
    @ViewModelKey(PomodoroCreatorViewModel::class)
    abstract fun bindPomodoroCreatorViewModel(viewModel: PomodoroCreatorViewModel): ViewModel
}