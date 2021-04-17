package com.example.gitufavorite

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import java.net.HttpURLConnection
import java.net.URL

internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var list = ArrayList<User>()

    override fun onCreate() {}

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        val cursor = mContext.contentResolver.query(
            DatabaseContract.FavoriteUserColumn.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val listConverted = MappingHelper.mapCursorToArrayList(cursor)
        list = (listConverted)
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.favorite_widget_item)
        val connect = URL(list[position].avatar_url).openConnection() as HttpURLConnection
        if (connect.responseCode == 200) {
            val bitmap = BitmapFactory.decodeStream(connect.inputStream)
            rv.setImageViewBitmap(R.id.imageView, bitmap)
        }

        val extras = bundleOf(
            FavoriteWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv

    }

    override fun getCount(): Int = list.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {}
}