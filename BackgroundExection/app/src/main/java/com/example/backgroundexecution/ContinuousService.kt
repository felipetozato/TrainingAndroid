package com.example.backgroundexecution

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ContinuousService : IntentService("ContinuousService") {
    override fun onHandleIntent(intent: Intent?) {
        while (true) {
            Log.v("BACKGROUN_EXECUTION", "[${Date()}] Executing services on thread: ${Thread.currentThread().name}")
            Thread.sleep(5000)
        }
    }

}

class ForegroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                NotificationChannel.DEFAULT_CHANNEL_ID,
                "Default",
                NotificationManager.IMPORTANCE_DEFAULT
            )
        }
        val notification = NotificationCompat.Builder(this.applicationContext, NotificationChannel.DEFAULT_CHANNEL_ID)
            .setContentTitle("Test Foreground")
            .setContentText("Foreground message")
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setTicker("Ticker text")
            .build()
        startForeground(100, notification)
    }

    private val handler = Executors.newSingleThreadScheduledExecutor()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val runnable = Runnable {
            while (true) {
                Log.v("FOREGROUND_EXECUTION", "[${Date()}] Executing services on thread: ${Thread.currentThread().name}")
                Thread.sleep(5000)
            }
        }
        handler.scheduleWithFixedDelay(runnable, 0, 4, TimeUnit.SECONDS)

        return START_NOT_STICKY
    }

}