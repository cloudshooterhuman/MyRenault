package com.renault.api.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.renault.domain.models.Car
import com.renault.domain.models.NetworkError
import com.renault.domain.models.NetworkException
import com.renault.domain.models.NetworkSuccess
import com.renault.domain.usecases.CarsUseCase

const val STARTING_PAGE_INDEX = 0

class CarPagingSource(private val getCarUseCase: CarsUseCase) :
    PagingSource<Int, Car>() {

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, Car>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Car> {
        val nextPage: Int = params.key ?: STARTING_PAGE_INDEX
//        Log.e("myapp", "PostPagingSource ${Thread.currentThread().name}")
        return when (val result = getCarUseCase.invoke(nextPage)) {
            is NetworkSuccess ->
                LoadResult.Page(
                    data = result.data,
                    prevKey = if (nextPage == STARTING_PAGE_INDEX) null else nextPage - 1,
                    nextKey = if (result.data.isEmpty()) null else nextPage + 1,
                )
            is NetworkError -> LoadResult.Error(Throwable(result.message))
            is NetworkException -> {
                LoadResult.Error(result.e)
            }
        }
    }
}