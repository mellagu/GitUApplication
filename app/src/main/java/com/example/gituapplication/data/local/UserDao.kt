package com.example.gituapplication.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    //insert data
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorite(favoriteUser: User)

    //for read data
    @Query("SELECT * FROM favorite_user ORDER BY id ASC")
    fun getFavoriteUser(): LiveData<List<User>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun removeFromFavorite(id: Int): Int

    //For Provider
    @Query("SELECT * FROM favorite_user")
    fun findAllOfThem(): Cursor


}


//Contains the method that used for accessing the database