package com.example.gituapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)

abstract class UserDatabase : RoomDatabase() {

    //
    abstract fun favoriteUserDAO(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        //We're going use the same instance for our database
        fun getDatabase(context: Context): UserDatabase {
            //if instance is not null then returning the instance
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}