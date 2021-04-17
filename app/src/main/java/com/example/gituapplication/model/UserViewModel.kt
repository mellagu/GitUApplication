package com.example.gituapplication.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gituapplication.api.RetrofitClient
import com.example.gituapplication.data.UserDetail
import com.example.gituapplication.data.UserResponse
import com.example.gituapplication.data.local.User
import com.example.gituapplication.data.local.UserDao
import com.example.gituapplication.data.local.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val listUser = MutableLiveData<ArrayList<User>>()
    val detailUser = MutableLiveData<UserDetail>()

    //room for favorite
    private var userDao: UserDao?
    private var userDB: UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(application)
        userDao = userDB?.favoriteUserDAO()
    }


    //setget Search API
    fun setSearchUser(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { Log.d("failure", it) }
                }

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body()?.items)
                    }
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUser
    }

    //SetGet Detail User
    fun setDetailUser(login: String) {
        RetrofitClient.apiInstance
            .getDetail(login)
            .enqueue(object : Callback<UserDetail> {
                override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                    t.message?.let { Log.d("failure", it) }
                }

                override fun onResponse(
                    call: Call<UserDetail>,
                    response: Response<UserDetail>
                ) {
                    if (response.isSuccessful) {
                        detailUser.postValue(response.body())
                    }
                }

            })
    }

    fun getDetailUsers(): LiveData<UserDetail> {
        return detailUser
    }

    fun addToFavorite(login : String, id : Int, avatarUrl: String, html_url: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = User(
                id,
                login,
                avatarUrl,
                html_url
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}