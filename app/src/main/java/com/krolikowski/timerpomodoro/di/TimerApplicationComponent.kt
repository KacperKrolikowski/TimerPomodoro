package com.krolikowski.timerpomodoro.di

import android.content.Context
import com.krolikowski.timerpomodoro.TimerPomodoroApp
import com.krolikowski.timerpomodoro.data.db.PomodoroDatabaseModule
import com.krolikowski.timerpomodoro.data.db.repositories.RepositoryModule
import com.krolikowski.timerpomodoro.data.sharedpreference.SharedPreferencesModule
import com.krolikowski.timerpomodoro.di.modules.ViewModelModule
import com.krolikowski.timerpomodoro.ui.main.MainActivityModule
import com.krolikowski.timerpomodoro.ui.pomodoro.PomodoroModule
import com.krolikowski.timerpomodoro.ui.pomodorocreator.PomodoroCreatorModule
import com.krolikowski.timerpomodoro.ui.pomodorolist.PomodoroListModule
import com.krolikowski.timerpomodoro.ui.settings.SettingsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        SharedPreferencesModule::class,
        RepositoryModule::class,
        SettingsModule::class,
        MainActivityModule::class,
        PomodoroModule::class,
        PomodoroListModule::class,
        PomodoroCreatorModule::class,
        ViewModelModule::class,
        PomodoroDatabaseModule::class
    ]
)

interface TimerApplicationComponent: AndroidInjector<TimerPomodoroApp> {

    override fun inject(instance: TimerPomodoroApp)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context
        ): TimerApplicationComponent
    }
}