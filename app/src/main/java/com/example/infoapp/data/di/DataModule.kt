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

package com.example.infoapp.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.example.infoapp.data.InfoRepository
import com.example.infoapp.data.DefaultInfoRepository
import com.example.infoapp.data.models.UserInfo
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsInfoRepository(
        infoRepository: FakeInfoRepository
    ): InfoRepository

}

class FakeInfoRepository @Inject constructor() : InfoRepository {
    override val infos: Flow<List<UserInfo>> = flowOf(fakeInfos)

    override suspend fun add(info: UserInfo) {
        throw NotImplementedError()
    }
}

val fakeInfos = listOf(
    UserInfo(
        name = "Harry Potter",
        email = "hp@yahoo.com",
        phoneNumber = "32155555",
        userName = "hp123",
        website = "www.hpotter.com"
    ),
    UserInfo(
        name = "Harry Potter",
        email = "hp@yahoo.com",
        phoneNumber = "32155555",
        userName = "hp123",
        website = "www.hpotter.com"
    ),
    UserInfo(
        name = "Bart Simpson",
        email = "hp@yahoo.com",
        phoneNumber = "32155555",
        userName = "hp123",
        website = "www.hpotter.com"
    ),
)
