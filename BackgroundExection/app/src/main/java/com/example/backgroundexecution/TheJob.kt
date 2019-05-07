package com.example.backgroundexecution

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class TheJob : JobService() {


    override fun onStopJob(params: JobParameters?): Boolean {
        scheduler.shutdown()
        return false
    }

    lateinit var scheduler: ExecutorService

    override fun onStartJob(params: JobParameters?): Boolean {
        scheduler = Executors.newSingleThreadExecutor()
        scheduler.submit {
            Log.i("TAG", "Job [${params?.jobId}] doing something in ${Thread.currentThread().name}")
            Thread.sleep(3000)
            Log.i("TAG", "Job finishing in ${Thread.currentThread().name}")
            jobFinished(params, false)
        }
        return true
    }
}