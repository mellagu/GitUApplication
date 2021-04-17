package com.example.gituapplication.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserDetail (
    val id : Int = 0,
    val name : String = "",
    val login : String= "",
    val html_url : String = "",
    val avatar_url : String = "",
    val followers : Int = 0,
    val following : Int = 0,
    val location : String = "",
    val company : String = "",
    val public_repos : Int = 0
) :Parcelable