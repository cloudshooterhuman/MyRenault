package com.renault.domain.repositories

import com.renault.domain.models.Car
import com.renault.domain.models.NetworkResult

interface CarsRepository {
    suspend fun getCars(nextPage : Int) : NetworkResult<List<Car>>
}