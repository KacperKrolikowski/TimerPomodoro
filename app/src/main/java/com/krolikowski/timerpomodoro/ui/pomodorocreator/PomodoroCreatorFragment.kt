package com.krolikowski.timerpomodoro.ui.pomodorocreator

import android.os.Bundle
import android.view.View
import com.krolikowski.timerpomodoro.databinding.FragmentPomodoroCreatorBinding
import com.krolikowski.timerpomodoro.helpers.core.BaseFragment

class PomodoroCreatorFragment: BaseFragment<FragmentPomodoroCreatorBinding, PomodoroCreatorViewModel>() {
    override val binding by lazy { FragmentPomodoroCreatorBinding.inflate(layoutInflater) }
    override val viewModelClass = PomodoroCreatorViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

        }
    }
}