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

package com.example.infoapp.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.repeatOnLifecycle
import com.example.infoapp.ui.theme.MyApplicationTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.Color
import com.example.infoapp.data.models.UserInfo
import com.example.infoapp.ui.composables.InfoItem

@Composable
fun InfoScreen(modifier: Modifier = Modifier, viewModel: InfoViewModel = hiltViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val items by produceState<InfoUiState>(
        initialValue = InfoUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }
    if (items is InfoUiState.Success) {
        InfoScreen(
            userInfoList = (items as InfoUiState.Success).data,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InfoScreen(
    userInfoList: List<UserInfo>,
    modifier: Modifier = Modifier
) {
    LazyColumn() {
        items(userInfoList) { userInfo ->
            InfoItem(
                name = userInfo.name,
                email = userInfo.email,
                userName = userInfo.userName,
                phoneNumber = userInfo.phoneNumber,
                userWebsite = userInfo.website,
                modifier = Modifier.background(Color.White)
            )
        }
    }
/*    Column(modifier) {
       var nameInfo by remember { mutableStateOf("Compose") }
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = nameInfo,
                onValueChange = { nameInfo = it }
            )
        }
        items.forEach {
            Text("Saved item: $it")
        }

    }*/
}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        InfoScreen()
    }
}
