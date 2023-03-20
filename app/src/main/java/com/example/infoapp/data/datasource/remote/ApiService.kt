package com.example.infoapp.data.datasource.remote

import com.example.infoapp.data.datasource.remote.responses.UserInfoDto
import com.example.infoapp.utils.USERS
import retrofit2.http.GET

interface ApiService {

    @GET(USERS)
    suspend fun getUserInfo(): List<UserInfoDto>

}