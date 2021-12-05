package com.krolikowski.timerpomodoro.ui.pomodorocreator

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.krolikowski.timerpomodoro.R
import com.krolikowski.timerpomodoro.data.db.entities.SinglePomodoro
import com.krolikowski.timerpomodoro.databinding.FragmentPomodoroCreatorBinding
import com.krolikowski.timerpomodoro.helpers.core.BaseFragment

class PomodoroCreatorFragment :
    BaseFragment<FragmentPomodoroCreatorBinding, PomodoroCreatorViewModel>() {
    override val binding by lazy { FragmentPomodoroCreatorBinding.inflate(layoutInflater) }
    override val viewModelClass = PomodoroCreatorViewModel::class.java

    private val navArgs: PomodoroCreatorFragmentArgs by navArgs()
    private lateinit var pomodoro: SinglePomodoro

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!navArgs.isNewPomodoro) setExistingPomodoro() else pomodoro = navArgs.pomodoroToEdit

        setValues()
        setListeners()
    }

    private fun setExistingPomodoro() {
        val argsPomodoro = navArgs.pomodoroToEdit
        pomodoro = SinglePomodoro(
            argsPomodoro.name,
            argsPomodoro.description,
            argsPomodoro.taskTime,
            argsPomodoro.workAmount,
            argsPomodoro.shortBreakTime,
            argsPomodoro.longBreakTime,
            argsPomodoro.longBreakOften
        )
    }

    private fun setValues() {
        binding.apply {
            pomodoroNameEdittext.setText(pomodoro.name)
            pomodoroDescriptionEdittext.setText(pomodoro.description)
            pomodoroTimePicker.apply {
                minValue = 1
                maxValue = 60
                value = pomodoro.taskTime
            }
            pomodoroQuantityPicker.apply {
                minValue = 1
                maxValue = 60
                value = pomodoro.workAmount
            }
            shortBreakTimeAdvPicker.apply {
                minValue = 1
                maxValue = 60
                value = pomodoro.shortBreakTime
            }
            longBreakTimeAdvPicker.apply {
                minValue = 1
                maxValue = 60
                value = pomodoro.longBreakTime
            }
            shortBreaksBeforeLongBreakPicker.apply {
                minValue = 1
                maxValue = 60
                value = pomodoro.longBreakOften
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            saveButton.setOnClickListener { updateValues() }
            deleteButton.setOnClickListener { deletePomodoro() }
            advanceOptions.setOnCheckedChangeListener { _, b ->
                if (b) openAdvanceSettings(true) else openAdvanceSettings()
            }
        }
    }

    private fun updateValues() {
        pomodoro.apply {
            name = binding.pomodoroNameEdittext.text.toString()
            description = binding.pomodoroDescriptionEdittext.text.toString()
            taskTime = binding.pomodoroTimePicker.value
            workAmount = binding.pomodoroQuantityPicker.value
            shortBreakTime = binding.shortBreakTimeAdvPicker.value
            longBreakTime = binding.longBreakTimeAdvPicker.value
            longBreakOften = binding.shortBreaksBeforeLongBreakPicker.value
        }
        savePomodoro()
    }

    private fun savePomodoro() {
        when {
            pomodoro.name.isEmpty() -> {
                hideKeyboard()
                Snackbar.make(requireView(), "Name can not be empty", Snackbar.LENGTH_SHORT).show()
            }
            navArgs.isNewPomodoro -> {
                viewModel.insertPomodoro(pomodoro)
                findNavController().navigate(R.id.action_pomodoroCreatorFragment_to_pomodoroListFragment)
            }
            !navArgs.isNewPomodoro -> {
                navArgs.pomodoroToEdit.apply {
                    name = pomodoro.name
                    description = pomodoro.description
                    taskTime = pomodoro.taskTime
                    workAmount = pomodoro.workAmount
                    shortBreakTime = pomodoro.shortBreakTime
                    longBreakTime = pomodoro.longBreakTime
                    longBreakOften = pomodoro.longBreakOften
                }
                navArgs.pomodoroToEdit.let { viewModel.insertPomodoro(it) }
                findNavController().navigate(R.id.action_pomodoroCreatorFragment_to_pomodoroListFragment)
            }
        }
    }

    private fun openAdvanceSettings(isAdvance: Boolean = false) {
        binding.apply {
            shortBreakTimeAdvTextview.isVisible = isAdvance
            shortBreakTimeAdvPicker.isVisible = isAdvance
            longBreakTimeAdvTextview.isVisible = isAdvance
            longBreakTimeAdvPicker.isVisible = isAdvance
            shortBreaksBeforeLongBreakTextview.isVisible = isAdvance
            shortBreaksBeforeLongBreakPicker.isVisible = isAdvance
        }
    }

    private fun deletePomodoro() {
        val backAction =
            PomodoroCreatorFragmentDirections.actionPomodoroCreatorFragmentToPomodoroListFragment()
        if (!navArgs.isNewPomodoro) navArgs.pomodoroToEdit.let { viewModel.deletePomodoro(it) }
        findNavController().navigate(backAction)
    }
}