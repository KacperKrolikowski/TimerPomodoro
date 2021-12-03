package com.krolikowski.timerpomodoro.data.db

import android.content.Context
import androidx.room.Room
import com.krolikowski.timerpomodoro.data.db.dao.PomodoroDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PomodoroDatabaseModule {

    @Provides
    @Singleton
    fun providePomodoroDatabase(context: Context): PomodoroDatabase{
        return Room.databaseBuilder(
            context,
            PomodoroDatabase::class.java,
            POMODORO_DB
        ).build()
    }

    @Provides
    @Singleton
    fun providePomodoroDao(pomodoroDatabase: PomodoroDatabase): PomodoroDao = pomodoroDatabase.getPomodoroDao()

    companion object{
        private const val POMODORO_DB = "POMODORO_DB"
    }
}