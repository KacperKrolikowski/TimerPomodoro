package com.krolikowski.timerpomodoro.ui.pomodorolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krolikowski.timerpomodoro.data.db.entities.SinglePomodoro
import com.krolikowski.timerpomodoro.data.db.repositories.PomodoroDatabaseRepository
import com.krolikowski.timerpomodoro.data.db.repositories.PomodoroDatabaseRepositoryType
import com.krolikowski.timerpomodoro.helpers.core.BaseViewModel
import javax.inject.Inject

class PomodoroListViewModel @Inject constructor(
    private val pomodoroDatabaseRepository: PomodoroDatabaseRepositoryType
) :BaseViewModel() {

    var pomodoroList: LiveData<List<SinglePomodoro>>

    init {
        pomodoroList = pomodoroDatabaseRepository.getAllPomodoros()
    }

    fun getAllPomodoros(){
        pomodoroList = pomodoroDatabaseRepository.getAllPomodoros()
    }
}