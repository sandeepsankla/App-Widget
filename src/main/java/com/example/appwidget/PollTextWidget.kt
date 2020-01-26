package com.example.appwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast

import com.example.appwidget.Constants.FIRST_LAYOUT_ID
import com.example.appwidget.Constants.FOURTH_LAYOUT_ID
import com.example.appwidget.Constants.SECOND_LAYOUT_ID
import com.example.appwidget.Constants.THIRD_LAYOUT_ID

/**
 * Implementation of App Widget functionality.
 */
class PollTextWidget : AppWidgetProvider() {

    // private static boolean isEnabled;
    private var currentLayout: Int = 0

    override fun onReceive(context: Context, intent: Intent?) {
        //super.onReceive(context, intent);
        if (intent == null || intent.extras == null) return
        // switch(intent.getExtras().getInt("layout")){
        val appWidgetManager = AppWidgetManager.getInstance(context)

        val remoteViews: RemoteViews
        val watchWidget: ComponentName

        remoteViews = RemoteViews(context.packageName, R.layout.new_app_widget)
        watchWidget = ComponentName(context, PollTextWidget::class.java!!)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(watchWidget)
        remoteViews.setInt(R.id.first_layout, "setBackgroundResource", R.drawable.bg_layout_unselected)
        remoteViews.setInt(R.id.second_layout, "setBackgroundResource", R.drawable.bg_layout_unselected)
        remoteViews.setInt(R.id.third_layout, "setBackgroundResource", R.drawable.bg_layout_unselected)
        remoteViews.setInt(R.id.fourth_layout, "setBackgroundResource", R.drawable.bg_layout_unselected)

        when (intent.action) {
            FIRST_LAYOUT_ID -> {
                currentLayout = 1
                remoteViews.setInt(R.id.first_layout, "setBackgroundResource", R.drawable.bg_layout_selected)
                remoteViews.setInt(R.id.first_layout, "setBackgroundResource", R.drawable.bg_layout_selected)
            }
            SECOND_LAYOUT_ID -> {
                currentLayout = 2
                remoteViews.setInt(R.id.second_layout, "setBackgroundResource", R.drawable.bg_layout_selected)
            }
            THIRD_LAYOUT_ID -> {
                currentLayout = 3
                remoteViews.setInt(R.id.third_layout, "setBackgroundResource", R.drawable.bg_layout_selected)
            }
            FOURTH_LAYOUT_ID -> {
                currentLayout = 4
                remoteViews.setInt(R.id.fourth_layout, "setBackgroundResource", R.drawable.bg_layout_selected)
            }
        }
        remoteViews.setOnClickPendingIntent(R.id.first_text, getPendingIntent(context, Constants.FIRST_BUTTON_CLICK, appWidgetIds, FIRST_LAYOUT_ID))
        remoteViews.setOnClickPendingIntent(R.id.second_text, getPendingIntent(context, Constants.SECOND_BUTTON_CLICK, appWidgetIds, SECOND_LAYOUT_ID))
        remoteViews.setOnClickPendingIntent(R.id.third_text, getPendingIntent(context, Constants.THIRD_BUTTON_CLICK, appWidgetIds, THIRD_LAYOUT_ID))
        remoteViews.setOnClickPendingIntent(R.id.fourth_text, getPendingIntent(context, Constants.FOURTH_BUTTON_CLICK, appWidgetIds, FOURTH_LAYOUT_ID))
        appWidgetManager.updateAppWidget(watchWidget, remoteViews)
        super.onReceive(context, intent)
        //if(intent.getAction().equalsIgnoreCase(Constants.FIRST_BUTTON_CLICK))
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            val remoteViews = RemoteViews(context.packageName,
                    R.layout.new_app_widget)
            remoteViews.setOnClickPendingIntent(R.id.first_text, getPendingIntent(context, Constants.FIRST_BUTTON_CLICK, appWidgetIds, "a"))
            remoteViews.setOnClickPendingIntent(R.id.second_text, getPendingIntent(context, Constants.SECOND_BUTTON_CLICK, appWidgetIds, "b"))
            remoteViews.setOnClickPendingIntent(R.id.third_text, getPendingIntent(context, Constants.THIRD_BUTTON_CLICK, appWidgetIds, "c"))
            remoteViews.setOnClickPendingIntent(R.id.fourth_text, getPendingIntent(context, Constants.FOURTH_BUTTON_CLICK, appWidgetIds, "d"))
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        currentLayout = -1
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        fun onRefreshLayout(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
            for (appWidgetId in appWidgetIds) {
                val remoteViews = RemoteViews(context.packageName,
                        R.layout.new_app_widget)
                remoteViews.setOnClickPendingIntent(R.id.first_text, getPendingIntent(context, Constants.FIRST_BUTTON_CLICK, appWidgetIds, FIRST_LAYOUT_ID))
                remoteViews.setOnClickPendingIntent(R.id.second_text, getPendingIntent(context, Constants.SECOND_BUTTON_CLICK, appWidgetIds, SECOND_LAYOUT_ID))
                remoteViews.setOnClickPendingIntent(R.id.third_text, getPendingIntent(context, Constants.THIRD_BUTTON_CLICK, appWidgetIds, THIRD_LAYOUT_ID))
                remoteViews.setOnClickPendingIntent(R.id.fourth_text, getPendingIntent(context, Constants.FOURTH_BUTTON_CLICK, appWidgetIds, FOURTH_LAYOUT_ID))
                Toast.makeText(context, "onRefreshed", Toast.LENGTH_SHORT).show()
                appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
            }
        }

        private fun getPendingIntent(context: Context, action: Int, appWidgetIds: IntArray, id: String): PendingIntent {
            //1
            val intent = Intent(context, PollTextWidget::class.java)
            intent.action = id
            intent.putExtra("layout", action)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
            return PendingIntent.getBroadcast(context, 0, intent, 0)
        }
    }

}

