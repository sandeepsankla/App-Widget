package com.example.appwidget

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast

/**
 * Implementation of App Widget functionality.
 */
class PollImagesWidget : AppWidgetProvider() {


    override fun onReceive(context: Context, intent: Intent) {

        val mgr = AppWidgetManager.getInstance(context)
        val appWidgetManager = AppWidgetManager.getInstance(context)

        if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val extras = intent.extras
            if (extras != null) {

                val remoteViews = RemoteViews(context.packageName, R.layout.poll_images_widget)
                val appWidgetIds = extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS)
                val position = extras.getInt(EXTRA_LABEL)
                if (appWidgetIds != null && appWidgetIds.size > 0) {
                    Toast.makeText(context, "REFRESH$position", Toast.LENGTH_SHORT).show()
                    remoteViews.setInt(appWidgetIds[position], "setBackgroundResource", R.drawable.bg_layout_selected)
                    appWidgetManager.updateAppWidget(appWidgetIds[position], remoteViews)
                    this.onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds)
                }
            }
        }

        super.onReceive(context, intent)

    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager,

                          appWidgetIds: IntArray) {


        // update each of the app widgets with the remote adapter

        for (i in appWidgetIds.indices) {
            val views = RemoteViews(
                    context.packageName,
                    R.layout.poll_images_widget)

            val intent = Intent(context, MyWidgetViewsService::class.java)
            views.setRemoteAdapter(R.id.widgetListView, intent)
            val refreshIntent = Intent(context, PollImagesWidget::class.java)
            refreshIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
            views.setPendingIntentTemplate(R.id.widgetListView, PendingIntent.getBroadcast(context, 0, refreshIntent, 0))
            appWidgetManager.updateAppWidget(appWidgetIds[i], views)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)

    }


    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        val EXTRA_LABEL = "TASK_TEXT"
        val UPDATE_MEETING_ACTION = "android.appwidget.action.APPWIDGET_UPDATE"
        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {

            val widgetText = context.getString(R.string.appwidget_text)
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.poll_images_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

