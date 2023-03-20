package com.example.infoapp.data.mappers

import com.example.infoapp.data.datasource.local.database.entities.UserInfoEntity
import com.example.infoapp.data.datasource.remote.responses.UserInfoDto
import com.example.infoapp.data.models.UserInfo

fun UserInfo.toEntity() = UserInfoEntity(name, email, phoneNumber, userName, website)

fun UserInfoDto.toModel() = UserInfo(
    name = name,
    email = email,
    phoneNumber = phone,
    userName = username,
    website = website
)

fun UserInfoEntity.toModel() = UserInfo(name, email, phoneNumber, userName, website)