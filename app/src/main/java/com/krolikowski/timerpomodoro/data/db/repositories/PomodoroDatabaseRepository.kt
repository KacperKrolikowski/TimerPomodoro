package com.krolikowski.timerpomodoro.data.db.repositories

import com.krolikowski.timerpomodoro.data.db.dao.PomodoroDao
import com.krolikowski.timerpomodoro.data.db.entities.SinglePomodoro
import javax.inject.Inject

class PomodoroDatabaseRepository @Inject constructor(
    private val pomodoroDao: PomodoroDao
): PomodoroDatabaseRepositoryType {
    override suspend fun insertPomodoro(pomodoro: SinglePomodoro) = pomodoroDao.insertPomodoro(pomodoro)
    override suspend fun deletePomodoro(pomodoro: SinglePomodoro) = pomodoroDao.deletePomodoro(pomodoro)
    override fun getAllPomodoros() = pomodoroDao.getAllPomodoros()
}