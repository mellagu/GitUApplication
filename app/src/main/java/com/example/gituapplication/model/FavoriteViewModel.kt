package com.example.gituapplication.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gituapplication.data.local.User
import com.example.gituapplication.data.local.UserDao
import com.example.gituapplication.data.local.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: UserDao?
    private var userDB: UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(application)
        userDao = userDB?.favoriteUserDAO()
    }

    fun getFavoriteUser(): LiveData<List<User>>? {
        return userDao?.getFavoriteUser()
    }
}