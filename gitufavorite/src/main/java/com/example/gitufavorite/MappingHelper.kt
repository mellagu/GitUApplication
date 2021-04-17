package com.example.gitufavorite

import android.database.Cursor

object MappingHelper {

    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<User>{
        val list = ArrayList<User>()
        if (cursor != null){
            while (cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.ID))
                val username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.USERNAME))
                val avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.AVATAR))
                val htmlUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.LINK_GIT))

                list.add(
                    User(
                        id,
                        username,
                        avatarUrl,
                        htmlUrl
                    )
                )
            }
        }
        return list
    }
}