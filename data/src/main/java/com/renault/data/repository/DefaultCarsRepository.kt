package com.renault.data.repository

import com.renault.api.services.CarsService
import com.renault.data.mapper.CarMapper
import com.renault.domaine.models.Car
import com.renault.domaine.models.NetworkError
import com.renault.domaine.models.NetworkException
import com.renault.domaine.models.NetworkResult
import com.renault.domaine.models.NetworkSuccess
import com.renault.domaine.repositories.CarsRepository
import javax.inject.Inject

class DefaultCarsRepository @Inject constructor(private val carService : CarsService, val carMapper: CarMapper) : CarsRepository
{
    override suspend fun getCars(nextPage: Int): NetworkResult<List<Car>> {
        return when(val carsResponse = carService.getCars(nextPage)) {
            is NetworkError -> {
                NetworkError(carsResponse.code, carsResponse.message)
            }

            is NetworkException -> {
                NetworkException(carsResponse.e)
            }

            is NetworkSuccess -> {
                NetworkSuccess(carMapper.fromListDto(carsResponse.data.data))
            }
        }
    }

}