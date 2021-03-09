package com.example.androiddevchallenge

import android.os.CountDownTimer
import android.util.Log

class TimerCountDown(private var timeRemaing : TimerVM) : CountDownTimer(timeRemaing.msecsRemaining.value?:0L,500) {

    override fun onFinish() {
        Log.d("Timer","Done")
    }

    override fun onTick(millisUntilFinished: Long) {

        val seconds = timeRemaing.msecsRemaining.value?:0L

        if ( seconds > 0L ) {
            timeRemaing.decreaseMSecs(500L)
            Log.d("Timer","Tick!")
        } else {
            Log.d("Timer","D O N E")
        }

    }

}