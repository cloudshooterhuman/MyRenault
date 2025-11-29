package com.renault.myrenault.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.renault.api.paging.CarPagingSource
import com.renault.domain.models.Car
import com.renault.domain.usecases.CarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 20

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postUseCase: CarsUseCase,
) : ViewModel() {
    var uiState: Flow<PagingData<Car>> =
        Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                CarPagingSource(
                    postUseCase,
                )
            },
        ).flow
            .cachedIn(viewModelScope)
            .onEach {
                Log.d("myapp", it.toString())
            }

    private fun getAllPosts() {
        uiState = Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                CarPagingSource(
                    postUseCase,
                )
            },
        ).flow
            .cachedIn(viewModelScope)
    }
}