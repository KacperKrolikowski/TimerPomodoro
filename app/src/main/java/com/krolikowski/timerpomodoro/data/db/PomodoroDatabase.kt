package com.krolikowski.timerpomodoro.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.krolikowski.timerpomodoro.data.db.dao.PomodoroDao
import com.krolikowski.timerpomodoro.data.db.entities.SinglePomodoro

@Database(
    entities = [SinglePomodoro::class],
    version = 1,
    exportSchema = false
)
abstract class PomodoroDatabase: RoomDatabase() {

    abstract fun getPomodoroDao(): PomodoroDao
}