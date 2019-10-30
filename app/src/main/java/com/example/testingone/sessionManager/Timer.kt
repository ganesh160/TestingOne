package com.karvy.atwl.sessionmanager

import android.content.Context
import android.util.Log
import com.example.testingone.sessionManager.TimerManager

internal open class Timer private constructor() {

    internal class Android private constructor(context: Context, timeoutInMillis: Int) : Timer() {
        private val timerManager: TimerManager
        private var isOnAlarmTriggered = false
        private var listener: TimezOutListener? = null



        private val managerListener = object : TimerManager.Listener {
            override fun onAlarmTriggered() {
                Log.d(TAG, "onAlarmTriggered()")
                isOnAlarmTriggered = true
                executeListenerIfRequired()
            }
        }

        init {
            timerManager = TimerManager(context.applicationContext, timeoutInMillis)
        }

        fun startTimer() {
            timerManager.startTimer(managerListener)
        }


        fun stopTimer() {
            timerManager.stopTimer()
        }

        fun register(listener: TimezOutListener) {
            this.listener = listener
            executeListenerIfRequired()
        }

        fun unregister() {
            this.listener = null
        }

        private fun executeListenerIfRequired() {
            if (listener != null && isOnAlarmTriggered) {
                listener!!.onTimeOut()
                isOnAlarmTriggered = false
            }
        }

        companion object {

            fun initialize(context: Context, timeoutInMillis: Int) {
                Timer.set(Timer.Android(context, timeoutInMillis))
            }

            fun get(): Android? {
                return Timer.get() as Timer.Android?
            }
        }
    }

    companion object {
        private val TAG = Timer::class.java.simpleName
        private val LOCK = Any()
        private var instance: Timer? = null

        private fun set(timer: Timer) {
            synchronized(LOCK) {
                check(instance == null) { "Timer is already initialized" }
                instance = timer
            }
        }

        private fun get(): Timer? {
            synchronized(LOCK) {
                return instance
            }
        }
    }
}
