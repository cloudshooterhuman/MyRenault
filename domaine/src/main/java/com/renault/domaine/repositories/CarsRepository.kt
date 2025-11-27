package com.renault.domaine.repositories

import com.renault.domaine.models.Car
import com.renault.domaine.models.NetworkResult
import kotlinx.coroutines.flow.Flow

interface CarsRepository {
    suspend fun getCars(nextPage : Int) : NetworkResult<List<Car>>
}