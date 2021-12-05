package com.krolikowski.timerpomodoro.ui.pomodorocreator

import com.krolikowski.timerpomodoro.data.db.entities.SinglePomodoro
import com.krolikowski.timerpomodoro.data.db.repositories.PomodoroDatabaseRepositoryType
import com.krolikowski.timerpomodoro.helpers.core.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PomodoroCreatorViewModel @Inject constructor(
    private val pomodoroDatabaseRepository: PomodoroDatabaseRepositoryType
) : BaseViewModel() {

    fun insertPomodoro(pomodoro: SinglePomodoro) =
        CoroutineScope(Dispatchers.Main).launch { pomodoroDatabaseRepository.insertPomodoro(pomodoro) }

    fun deletePomodoro(pomodoro: SinglePomodoro) =
        CoroutineScope(Dispatchers.Main).launch { pomodoroDatabaseRepository.deletePomodoro(pomodoro) }

}