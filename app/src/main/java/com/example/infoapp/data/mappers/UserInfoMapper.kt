package com.example.infoapp.data.mappers

import com.example.infoapp.data.local.database.UserInfoEntity
import com.example.infoapp.data.models.UserInfo

fun UserInfo.toEntity() = UserInfoEntity(name, email, phoneNumber, userName, website)

fun UserInfoEntity.toModel() = UserInfo(name, email, phoneNumber, userName, website)