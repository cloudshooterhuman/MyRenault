package com.renault.api.services

import com.renault.domain.models.NetworkResult
import com.renault.api.models.CarDto
import com.renault.api.models.Page
import retrofit2.http.GET
import retrofit2.http.Query

interface CarsService {
    @GET("?cmd=getModels&make=ford")
    suspend fun getCars(@Query("page") page: Int): NetworkResult<Page<CarDto>>
}