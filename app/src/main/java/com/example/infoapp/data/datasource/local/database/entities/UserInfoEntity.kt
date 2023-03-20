package com.example.infoapp.data.datasource.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "info")
data class UserInfoEntity(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val userName: String,
    val website: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}