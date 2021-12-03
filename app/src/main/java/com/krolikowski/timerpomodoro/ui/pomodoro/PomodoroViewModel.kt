package com.krolikowski.timerpomodoro.ui.pomodoro

import androidx.lifecycle.ViewModel
import com.krolikowski.timerpomodoro.data.sharedpreference.SharedPreferenceRepository
import com.krolikowski.timerpomodoro.helpers.core.BaseViewModel
import javax.inject.Inject

class PomodoroViewModel @Inject constructor(
    private val sharedPreferenceRepository: SharedPreferenceRepository
): BaseViewModel() {

    fun getTimeFormSharedPreferences() = sharedPreferenceRepository.getTime()
    fun getQuantityFromSharedPreferences() = sharedPreferenceRepository.getQuantity()

    fun getTimerSecondsRemaining() = sharedPreferenceRepository.getTimerSecondsRemaining()
    fun saveTimerSecondsRemaining(time: Long) = sharedPreferenceRepository.saveTimerSecondsRemaining(time)
}