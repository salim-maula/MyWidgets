package com.salim.mywidgets

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    companion object{
        private const val JOB_ID = 100
        private const val SCHEDULE_OF_PERIOD = 86000L
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btn_start)
        val btnStop = findViewById<Button>(R.id.btn_stop)

        btnStart.setOnClickListener { startJob() }
        btnStop.setOnClickListener { cancelJob() }
    }

    private fun cancelJob() {
        val tm = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        tm.cancel(JOB_ID)
        Toast.makeText(this, "job Service canceled", Toast.LENGTH_SHORT).show()
    }

    private fun startJob() {
        val mServicecomponent = ComponentName(this, UpdateWidgetService::class.java)

        val builder = JobInfo.Builder(JOB_ID, mServicecomponent)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            builder.setPeriodic(900000)
        }else{
            builder.setPeriodic(SCHEDULE_OF_PERIOD)
        }
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(builder.build())

        Toast.makeText(this, "Job Service started", Toast.LENGTH_SHORT).show()
    }
}