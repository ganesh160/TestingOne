package com.karvy.atwl.sessionmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.ResultReceiver
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.testingone.sessionManager.TimerManager

open class TimezOutBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val resultReceiver = intent.getParcelableExtra<ResultReceiver>(TimerManager.RESULT_RECEIVER)
        /*if (resultReceiver != null) {
      resultReceiver.send(0, null);
    }*/
        context.sendBroadcast(Intent("TimeOut"))
    }

    companion object {
        val TIMEZOUT_BROADCAST_REQUEST_CODE : Int = 0x1990
    }
}
