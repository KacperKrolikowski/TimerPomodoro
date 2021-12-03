package com.krolikowski.timerpomodoro.ui.settings

import androidx.lifecycle.ViewModel
import com.krolikowski.timerpomodoro.di.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SettingsModule {

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel
}