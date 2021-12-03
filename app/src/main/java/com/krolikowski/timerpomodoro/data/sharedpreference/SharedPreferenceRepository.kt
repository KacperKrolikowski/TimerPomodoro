package com.krolikowski.timerpomodoro.data.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import java.security.AccessControlContext
import javax.inject.Inject

class SharedPreferenceRepository @Inject constructor(
    private val context: Context
) {
    private val pref: Lazy<SharedPreferences> = lazy {
        context.applicationContext.getSharedPreferences(
            KEY_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun setTime(timeInMinutes: Int){
        pref.value.edit().apply(){
            putInt(KEY_PREFERENCES_TIME_OF_QUICK_POMODORO, timeInMinutes)
            apply()
        }
    }

    fun getTime() = pref.value.getInt(KEY_PREFERENCES_TIME_OF_QUICK_POMODORO, 25)


    fun setQuantity(quantity: Int){
        pref.value.edit().apply(){
            putInt(KEY_PREFERENCES_QUANTITY_OF_QUICK_POMODORO, quantity)
            apply()
        }
    }

    fun getQuantity() = pref.value.getInt(KEY_PREFERENCES_QUANTITY_OF_QUICK_POMODORO, 5)


    fun saveTimerSecondsRemaining(time: Long) {
        pref.value.edit().apply(){
            putLong(KEY_PREFERENCES_TIMER_REMAINING_TIME, time)
            apply()
        }
    }

    fun getTimerSecondsRemaining() = pref.value.getLong(KEY_PREFERENCES_TIMER_REMAINING_TIME, 0L)

    companion object {
        private const val KEY_PREFERENCES_NAME = "TimerPomodoroApp"
        private const val KEY_PREFERENCES_TIME_OF_QUICK_POMODORO = "TimeOfQuickPomodoro"
        private const val KEY_PREFERENCES_QUANTITY_OF_QUICK_POMODORO = "QuantityOfQuickPomodoro"
        private const val KEY_PREFERENCES_TIMER_REMAINING_TIME = "TimerRemainingTime"
    }
}