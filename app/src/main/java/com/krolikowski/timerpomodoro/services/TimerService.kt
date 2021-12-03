package com.krolikowski.timerpomodoro.services

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log

class TimerService: Service() {

    private lateinit var timer: CountDownTimer

    private var timeToCount = 0L

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        /*
        val notificationIntent = Intent(this, QuickPomodoroFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = NotificationCompat.Builder(this, "TimerService")
            .setContentTitle("Pomodoro timer")
            .setContentText("00:00")
            .setContentIntent(pendingIntent)
            .build()

         */

        timeToCount = intent!!.getLongExtra("TIME_TO_COUNT", 25L)
        val timerTask = intent.getSerializableExtra("TIMER_TASK")

        timer = object : CountDownTimer(timeToCount * 1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                timeToCount = millisUntilFinished / 1000
                Log.d("DEBUG_SERVICE", timeToCount.toString())
                val backIntent = Intent(TIMER_UPDATE)
                backIntent.putExtra(TIME_EXTRA, timeToCount)
                sendBroadcast(backIntent)
            }

            override fun onFinish() {
                val finishIntent = Intent(TIMER_STATUS)
                finishIntent.putExtra(TIMER_STATUS_VAL, timerTask)
                sendBroadcast(finishIntent)
            }
        }.start()
        //startForeground(1, notification)

        return START_NOT_STICKY
    }

    override fun onDestroy() {

        timer.cancel()

        val backTimeIntent = Intent(TIMER_STATE)
        backTimeIntent.putExtra(TIMER_PAUSED_TIME, timeToCount)
        sendBroadcast(backTimeIntent)

        Log.d("DEBUG_SERVICE2", timeToCount.toString())


        super.onDestroy()
    }

    companion object{
        const val TIMER_UPDATE = "timerUpdate"
        const val TIME_EXTRA = "timeExtra"
        const val TIMER_STATE = "timerState"
        const val TIMER_PAUSED_TIME = "timerPausedTime"
        const val TIMER_STATUS = "timerStatus"
        const val TIMER_STATUS_VAL = "timerStatusVal"
    }

}