package com.krolikowski.timerpomodoro.data.db.repositories

import androidx.lifecycle.LiveData
import com.krolikowski.timerpomodoro.data.db.entities.SinglePomodoro

interface PomodoroDatabaseRepositoryType {
    suspend fun insertPomodoro(pomodoro: SinglePomodoro)
    suspend fun deletePomodoro(pomodoro: SinglePomodoro)
    fun getAllPomodoros(): LiveData<List<SinglePomodoro>>
}