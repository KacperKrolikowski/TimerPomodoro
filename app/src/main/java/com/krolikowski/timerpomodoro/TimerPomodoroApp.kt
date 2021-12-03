package com.krolikowski.timerpomodoro

import androidx.viewbinding.BuildConfig
import com.krolikowski.timerpomodoro.di.DaggerTimerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class TimerPomodoroApp: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerTimerApplicationComponent.factory().create(this)
    }
}