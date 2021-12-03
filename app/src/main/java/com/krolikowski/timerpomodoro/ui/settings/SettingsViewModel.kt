package com.krolikowski.timerpomodoro.ui.settings

import com.krolikowski.timerpomodoro.data.sharedpreference.SharedPreferenceRepository
import com.krolikowski.timerpomodoro.helpers.core.BaseViewModel
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val sharedPreferenceRepository: SharedPreferenceRepository
): BaseViewModel() {

    fun getTimeFormSharedPreferences() = sharedPreferenceRepository.getTime()
    fun getQuantityFromSharedPreferences() = sharedPreferenceRepository.getQuantity()

    fun setTime(seconds: Int) = sharedPreferenceRepository.setTime(seconds)
    fun setQuantity(quantity: Int) = sharedPreferenceRepository.setQuantity(quantity)

}