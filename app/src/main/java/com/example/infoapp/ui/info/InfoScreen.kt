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

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.repeatOnLifecycle
import com.example.infoapp.ui.theme.MyApplicationTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.infoapp.R
import com.example.infoapp.data.models.UserInfo
import com.example.infoapp.ui.composables.DefaultAppBar
import com.example.infoapp.ui.composables.InfoItem
import com.example.infoapp.ui.composables.SearchAppBar
import com.example.infoapp.utils.SearchWidgetState

@Composable
fun InfoScreen(modifier: Modifier = Modifier, infoViewModel: InfoViewModel = hiltViewModel()) {
    val searchWidgetState by infoViewModel.searchWidgetState
    val searchTextState by infoViewModel.searchTextState
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val items by produceState<InfoUiState>(
        initialValue = InfoUiState.Loading,
        key1 = lifecycle,
        key2 = infoViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = STARTED) {
            infoViewModel.uiState.collect { value = it }
        }
    }
    if (items is InfoUiState.Success) {
        InfoScreen(
            infoViewModel = infoViewModel,
            userInfoList = (items as InfoUiState.Success).data,
            modifier = modifier,
            searchWidgetState = searchWidgetState,
            searchTextState = searchTextState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InfoScreen(
    infoViewModel: InfoViewModel,
    userInfoList: List<UserInfo>,
    modifier: Modifier = Modifier,
    searchWidgetState: SearchWidgetState,
    searchTextState: String
) {
    Scaffold(
        topBar = {
            CurrentAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = { infoViewModel.updateSearchTextState(it) },
                onCloseClicked = { infoViewModel.updateSearchWidgetState(SearchWidgetState.CLOSED) },
                onSearchClicked = { Log.d("Search text", it) },
                onSearchTriggered = { infoViewModel.updateSearchWidgetState(SearchWidgetState.OPENED) }
            )
        }
    ) {
        LazyColumn(modifier = modifier.padding(it)) {
            items(userInfoList) { userInfo ->
                InfoItem(
                    name = userInfo.name,
                    email = userInfo.email,
                    userName = userInfo.userName,
                    phoneNumber = userInfo.phoneNumber,
                    userWebsite = userInfo.website,
                    modifier = Modifier.background(Color.White).fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CurrentAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                title = stringResource(id = R.string.top_app_bar_title),
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChanged = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

// Previews
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        InfoScreen()
    }
}
