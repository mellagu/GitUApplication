package com.example.gituapplication.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gituapplication.api.RetrofitClient
import com.example.gituapplication.data.local.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    val listFollow = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(user: String){
        RetrofitClient.apiInstance
            .getFollowers(user)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        listFollow.postValue(response.body())
                    }
                }

            })

    }
    fun getListFollowers(): LiveData<ArrayList<User>>{
        return listFollow
    }

}