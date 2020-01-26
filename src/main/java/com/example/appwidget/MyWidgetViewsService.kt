package com.example.appwidget

import android.content.Intent
import android.widget.RemoteViewsService

class MyWidgetViewsService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return MyWidgetViewsFactory(this.applicationContext)
    }
}
