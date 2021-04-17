package com.example.gituapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class User(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatar_url : String,
    val html_url : String

): Serializable