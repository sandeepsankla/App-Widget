package com.example.appwidget

import androidx.appcompat.app.AppCompatActivity

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Handler

import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val man = AppWidgetManager.getInstance(this)
        // 2
        val ids = man.getAppWidgetIds(
                ComponentName(this, PollTextWidget::class.java))
        // 3
        //  Intent updateIntent =  new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // 4
        //  updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        // 5
        //  sendBroadcast(updateIntent);*/


        val handler = Handler()
        val timer = Timer()

        val task = object : TimerTask() {

            override fun run() {
                handler.post {
                    val appWidgetManager = AppWidgetManager.getInstance(applicationContext)
                    PollTextWidget.onRefreshLayout(applicationContext, appWidgetManager, ids)
                }
            }
        }
        timer.scheduleAtFixedRate(task, 0, 5000)
    }
}
