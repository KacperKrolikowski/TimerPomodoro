package com.krolikowski.timerpomodoro.ui.settings

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.krolikowski.timerpomodoro.databinding.FragmentSettingsBinding
import com.krolikowski.timerpomodoro.helpers.core.BaseFragment

class SettingsFragment: BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override val binding: FragmentSettingsBinding by lazy { FragmentSettingsBinding.inflate(layoutInflater) }
    override val viewModelClass = SettingsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPreviousValues()
        setChangeListeners()
    }

    private fun setPreviousValues(){
        binding.quickPomodoroSettingsTime.setText(viewModel.getTimeFormSharedPreferences().toString())
        binding.quickPomodoroSettingsQuantity.setText(viewModel.getQuantityFromSharedPreferences().toString())
    }

    private fun setChangeListeners(){
        binding.quickPomodoroSettingsSaveButton.setOnClickListener {
            viewModel.setTime(binding.quickPomodoroSettingsTime.text.toString().toInt())
            viewModel.setQuantity(binding.quickPomodoroSettingsQuantity.text.toString().toInt())
        }
    }
}