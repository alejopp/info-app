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
import com.example.infoapp.data.local.database.InfoDao
import com.example.infoapp.data.local.database.UserInfoEntity
import com.example.infoapp.data.mappers.toEntity
import com.example.infoapp.data.mappers.toModel
import com.example.infoapp.data.models.UserInfo
import javax.inject.Inject

interface InfoRepository {
    val infos: Flow<List<UserInfo>>
    suspend fun add(info: UserInfo)
}

class DefaultInfoRepository @Inject constructor(
    private val infoDao: InfoDao
): InfoRepository {

    override val infos: Flow<List<UserInfo>> =
        infoDao.getInfoList().map { userInfoEntityList ->
            userInfoEntityList.map { userInfoEntity -> userInfoEntity.toModel() }
        }

    override suspend fun add(info: UserInfo) {
        infoDao.insertInfo(info.toEntity())
    }
}