package com.example.gitufavorite

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.example.gituapplication"
    const val SCHEMA = "content"

    internal class FavoriteUserColumn : BaseColumns {
        companion object {
            private const val TABLE_NAME = "favorite_user"
            const val ID = "id"
            const val USERNAME = "login"
            const val AVATAR = "avatar_url"
            const val LINK_GIT = "html_url"

            val CONTENT_URI = Uri.Builder().scheme(SCHEMA)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}