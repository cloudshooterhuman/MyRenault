/*
 * Copyright 2024 Abdellah Selassi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.renault.myrenault.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SwipeUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.cleancompose.ui.components.EmptyListIndicator
import com.cleancompose.ui.components.LoadingIndicator
import com.cleancompose.ui.components.NetworkErrorIndicator
import com.cleancompose.ui.components.CarItem
import com.renault.myrenault.app.R
import com.renault.myrenault.viewmodel.CarViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarScreen(
    modifier: Modifier = Modifier,
    viewModel: CarViewModel = hiltViewModel(),
) {
    val lazyPagingPosts = viewModel.uiState.collectAsLazyPagingItems()

    var itemCount by remember { mutableIntStateOf(15) }
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            // fetch something
            delay(1500)
            itemCount += 5
            pullToRefreshState.endRefresh()
        }
    }

    val listState = rememberLazyListState()
    val showUpButton by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    val scope = rememberCoroutineScope()


    Box(modifier) {
        LazyColumn(state = listState) {
            when (val state = lazyPagingPosts.loadState.refresh) {
                is LoadState.NotLoading -> {
                    val isEmptyPostList = lazyPagingPosts.itemCount == 0
                    if (isEmptyPostList) {
                        item {
                            EmptyListIndicator(modifier)
                        }
                    }
                }

                is LoadState.Loading -> {
                    item {
                        LoadingIndicator(modifier)
                    }
                }

                is LoadState.Error -> {
                    item {
                        NetworkErrorIndicator(
                            state.error.message ?: stringResource(R.string.unknwon_error),
                            modifier,
                        ) { lazyPagingPosts.retry() }
                    }
                }
            }

            if (!pullToRefreshState.isRefreshing) {
                if (lazyPagingPosts.itemCount > 0) {
                    items(
                        lazyPagingPosts.itemCount,
                        key = lazyPagingPosts.itemKey { it.title },
                    ) { index ->
                        lazyPagingPosts[index]?.let {
                            CarItem(it, {
                                val imageUrl = it.imageUrl
                                val encodedUrl =
                                    URLEncoder.encode(
                                        imageUrl,
                                        StandardCharsets.UTF_8.toString(),
                                    )

                            })
                        }
                    }
                }
            }
        }

        if (showUpButton) {
            SmallFloatingActionButton(
                modifier = Modifier
                    .navigationBarsPadding()
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.SwipeUp,
                    contentDescription = stringResource(id = R.string.publication_date),
                    modifier = Modifier.size(32.dp),
                )
            }
        }
    }
}
