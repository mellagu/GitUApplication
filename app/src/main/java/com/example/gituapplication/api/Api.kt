package com.example.gituapplication.api

import com.example.gituapplication.data.UserDetail
import com.example.gituapplication.data.UserResponse
import com.example.gituapplication.data.local.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    //API SEARCH
    @GET("search/users")
    @Headers("Authorization: token a5e804749b8a3ef828df2e424edaf21988221f47")
    fun getSearchUsers(
        @Query("q")query: String
    ) : Call<UserResponse>

    //API DETAIL
    @GET("users/{login}")
    fun getDetail(
        @Path("login")query: String
    ) : Call<UserDetail>

    //API Followers
    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login")query: String
    ) : Call<ArrayList<User>>

    //API Following
    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login")query: String
    ) : Call<ArrayList<User>>

}