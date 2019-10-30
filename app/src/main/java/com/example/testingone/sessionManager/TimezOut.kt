package com.karvy.atwl.sessionmanager

import android.content.Context
import com.example.testingone.Util.AppController
import java.lang.Exception

open class TimezOut {

    companion object {
        fun initialize(context: Context, configuration: Configuration) {
            Timer.Android.initialize(context, configuration.timeOutInMillis)
        }


        fun startTimer() {
            Timer.Android.get()!!.startTimer()
        }

        fun stopTimer() {
            Timer.Android.get()!!.stopTimer()
        }

        fun restartTimer() {
            Timer.Android.get()!!.stopTimer()
            Timer.Android.get()!!.startTimer()
        }

        fun register(listener: TimezOutListener) {
            Timer.Android.get()!!.register(listener)
        }

        fun unregister() {
            Timer.Android.get()!!.unregister()
        }

        @JvmStatic
        fun initialize(appController: AppController, configuration: TimezOut.Configuration) {
            Timer.Android.initialize(appController, configuration.timeOutInMillis)
        }

    }

    class Configuration(val timeOutInMillis: Int)
}
