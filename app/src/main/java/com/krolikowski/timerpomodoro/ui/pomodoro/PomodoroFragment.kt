package com.krolikowski.timerpomodoro.ui.pomodoro

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.krolikowski.timerpomodoro.R
import com.krolikowski.timerpomodoro.data.db.entities.SinglePomodoro
import com.krolikowski.timerpomodoro.databinding.FragmentPomodoroBinding
import com.krolikowski.timerpomodoro.helpers.core.BaseFragment
import com.krolikowski.timerpomodoro.services.TimerService
import com.krolikowski.timerpomodoro.utils.TimerState
import com.krolikowski.timerpomodoro.utils.TimerTask

class PomodoroFragment: BaseFragment<FragmentPomodoroBinding, PomodoroViewModel>() {
    override val binding by lazy { FragmentPomodoroBinding.inflate(layoutInflater) }
    override val viewModelClass = PomodoroViewModel::class.java

    private val navArgs: PomodoroFragmentArgs by navArgs()
    private lateinit var currentPomodoro: SinglePomodoro

    private lateinit var serviceIntent: Intent

    private var timerState = TimerState.Stopped
    private var whatsNow = "Pomodoro"
    private var timeOfSinglePomodoro = 10L
    private var timeOfShortBreak = 3L
    private var timeOfLongBreak = 7L
    private var quantityOfPomodoros = 4
    private var numberOfFinishedPomodoro = 1
    private var numberOfShortBreaksBeforeLongBreak = 3
    private var numberOfShortBreaks = 3

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (navArgs.isQuickPomodoro) setQuickPomodoro() else setSelectedPomodoro()

        setListeners()
        setService()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().unregisterReceiver(updateTime)
    }

    private fun setListeners(){
        binding.apply {
            startFab.setOnClickListener { startPomodoro() }
            pauseFab.setOnClickListener { pausePomodoro() }
            stopFab.setOnClickListener { stopPomodoro() }
        }
    }

    private fun setQuickPomodoro(){
        timeOfSinglePomodoro = viewModel.getTimeFormSharedPreferences().toLong()
        quantityOfPomodoros = viewModel.getQuantityFromSharedPreferences()
        binding.pomodoroNameTV.text = getString(R.string.quick_pomodoro)
        binding.mainTimer.text = "$timeOfSinglePomodoro:00"
        binding.counter.text = "1/$quantityOfPomodoros"
    }

    private fun setSelectedPomodoro(){
        currentPomodoro = navArgs.pomodoroInstance!!
        with(currentPomodoro){
            timeOfSinglePomodoro = this.taskTime.toLong()
            timeOfShortBreak = this.shortBreakTime.toLong()
            timeOfLongBreak = this.longBreakTime.toLong()
            quantityOfPomodoros = this.workAmount
            numberOfShortBreaksBeforeLongBreak = this.longBreakOften
            binding.pomodoroNameTV.text = this.name
            binding.mainTimer.text = "${this.taskTime}:00"
            binding.counter.text = "1/${this.workAmount}"
        }
    }

    private fun setService(){
        serviceIntent = Intent(requireContext(), TimerService::class.java)
        requireContext().registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATE))
        requireContext().registerReceiver(timerStatusControlReceiver, IntentFilter(TimerService.TIMER_STATUS))
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val receivedTime = intent!!.getLongExtra(TimerService.TIME_EXTRA, 0L)
            updateUI(receivedTime)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(newTime: Long){
        val minutesUntilFinished = newTime/60
        val secondsInMinuteUntilFinished = newTime - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()

        binding.mainTimer.text =
            "$minutesUntilFinished:${
                if (secondsStr.length == 2)
                    secondsStr
                else "0$secondsStr"
            }"
    }

    private val pausedTimer: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val pausedTime = intent!!.getLongExtra(TimerService.TIMER_PAUSED_TIME, 0L)
            viewModel.saveTimerSecondsRemaining(pausedTime)

        }
    }

    private val timerStatusControlReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val previousTimerTask = intent!!.getSerializableExtra(TimerService.TIMER_STATUS_VAL)
            if (previousTimerTask == TimerTask.Pomodoro){
                updatePomodoroCounter()
                when {
                    numberOfFinishedPomodoro == (quantityOfPomodoros + 1) -> {
                        finishedPomodoro()
                    }
                    numberOfShortBreaks != 0 -> {
                        startTimerFromStopped(timeOfShortBreak, TimerTask.ShortBreak)
                        numberOfShortBreaks--
                    }
                    numberOfShortBreaks == 0 -> {
                        startTimerFromStopped(timeOfLongBreak, TimerTask.LongBreak)
                        numberOfShortBreaks = numberOfShortBreaksBeforeLongBreak
                    }
                }
            } else{
                startTimerFromStopped(timeOfSinglePomodoro, TimerTask.Pomodoro)
            }
        }
    }

    private fun startTimerFromStopped(time: Long, nextTimerTask: TimerTask = TimerTask.Pomodoro){
        serviceIntent.putExtra("TIME_TO_COUNT", time)
        serviceIntent.putExtra("TIMER_TASK", nextTimerTask)
        requireContext().startService(serviceIntent)
        timerState = TimerState.Running
        updateButtons()
        updateStateText(nextTimerTask)
    }

    private fun updatePomodoroCounter(){
        numberOfFinishedPomodoro++
        binding.counter.text = "$numberOfFinishedPomodoro/$quantityOfPomodoros"
        Log.d(deb_pom, "$numberOfFinishedPomodoro/$quantityOfPomodoros")
    }

    private fun startPomodoro(){
        when(timerState){
            TimerState.Stopped -> startTimerFromStopped(timeOfSinglePomodoro)
            TimerState.Paused -> {
                val previousTime = viewModel.getTimerSecondsRemaining()
                serviceIntent.putExtra("TIME_TO_COUNT", previousTime)
                requireContext().startService(serviceIntent)
                timerState = TimerState.Running
                updateButtons()
            }
            TimerState.Running -> Unit
        }
    }

    private fun pausePomodoro(){
        timerState = TimerState.Paused

        requireContext().registerReceiver(pausedTimer, IntentFilter(TimerService.TIMER_STATE))
        requireContext().stopService(serviceIntent)

        updateButtons()
    }

    private fun stopPomodoro(){
        timerState = TimerState.Stopped
        requireContext().stopService(serviceIntent)
        updateButtons()
    }

    private fun updateButtons(){
        when(timerState){
            TimerState.Running ->{
                binding.startFab.isEnabled = false
                binding.pauseFab.isEnabled = true
                binding.stopFab.isEnabled = true
            }
            TimerState.Stopped ->{
                binding.startFab.isEnabled = true
                binding.pauseFab.isEnabled = false
                binding.stopFab.isEnabled = false
            }
            TimerState.Paused ->{
                binding.startFab.isEnabled = true
                binding.pauseFab.isEnabled = false
                binding.stopFab.isEnabled = true
            }
        }
    }

    private fun updateStateText(timerTask: TimerTask){
        binding.stateNameTV.text = when (timerTask) {
                TimerTask.Pomodoro -> "Pomodoro"
                TimerTask.ShortBreak -> "Short break"
                TimerTask.LongBreak -> "Long break"
            }
    }

    private fun finishedPomodoro(){

    }

    companion object{
        const val deb_pom = "DEBUG_POMODORO"
    }
}