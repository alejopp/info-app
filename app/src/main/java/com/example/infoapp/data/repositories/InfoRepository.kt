/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.infoapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.infoapp.data.datasource.local.database.InfoDao
import com.example.infoapp.data.datasource.remote.ApiService
import com.example.infoapp.data.mappers.toEntity
import com.example.infoapp.data.mappers.toModel
import com.example.infoapp.data.models.UserInfo
import javax.inject.Inject

interface InfoRepository {
    val infos: Flow<List<UserInfo>>
    suspend fun add(infoList: List<UserInfo>)
    suspend fun getUserInfoList()
}

class InfoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val infoDao: InfoDao
) : InfoRepository {

    override val infos: Flow<List<UserInfo>> =
        infoDao.getInfoList().map { userInfoEntityList ->
            userInfoEntityList.map { userInfoEntity -> userInfoEntity.toModel() }
        }

    override suspend fun add(infoList: List<UserInfo>) {
        infoDao.insertInfo(infoList.map { userInfo ->
            userInfo.toEntity()
        })
    }

    override suspend fun getUserInfoList() {
        try {
            val response = apiService.getUserInfo().map { userInfoDto ->
                userInfoDto.toModel()
            }
            //Log.d("InfoRepository", "$response")
            //println("InfoRepository $response")
            add(response)
        } catch (e: Exception) {
            //println(e.message)
        }
    }
}
