package com.krolikowski.timerpomodoro.data.sharedpreference

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(applicationContext: Context) = SharedPreferenceRepository(applicationContext)
}