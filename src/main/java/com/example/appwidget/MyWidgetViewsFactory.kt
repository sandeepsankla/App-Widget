package com.example.appwidget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService

import java.util.ArrayList
import java.util.Arrays

class MyWidgetViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {
    //private Cursor mCursor;
    private var namesData: List<String>? = null

    override fun onCreate() {
        namesData = ArrayList()
        namesData = Arrays.asList(*mContext.resources.getStringArray(R.array.player_names_array))
    }

    override fun onDataSetChanged() {

    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        return if (namesData == null) 0 else namesData!!.size
    }


    override fun getViewAt(position: Int): RemoteViews? {
        if (position == AdapterView.INVALID_POSITION || namesData == null) {
            return null
        }

        val rv = RemoteViews(mContext.packageName, R.layout.poll_list_item)
        rv.setTextViewText(R.id.name_text, namesData!![position])

        val fillInIntent = Intent()
        fillInIntent.putExtra(PollImagesWidget.EXTRA_LABEL, position)
        fillInIntent.action = PollImagesWidget.UPDATE_MEETING_ACTION
        rv.setOnClickFillInIntent(R.id.root_layout, fillInIntent)

        return rv
    }


    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return namesData!![position].hashCode().toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

}
