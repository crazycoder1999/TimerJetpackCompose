package com.example.androiddevchallenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerVM(msecs : Long) : ViewModel() {
    private var _msecsRemaining = MutableLiveData<Long>(msecs)
    var msecsRemaining : LiveData<Long> = _msecsRemaining

    fun decreaseMSecs(amount : Long) {
        Log.d("Timer","Dec... " + _msecsRemaining.value + "")
        val value = _msecsRemaining.value?:0L
        _msecsRemaining.value = value - amount
    }
}