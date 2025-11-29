package com.renault.data.repository

import com.renault.api.models.CarDto
import com.renault.api.services.CarsService
import com.renault.data.mapper.CarMapper
import com.renault.domain.models.Car
import com.renault.domain.models.NetworkError
import com.renault.domain.models.NetworkException
import com.renault.domain.models.NetworkResult
import com.renault.domain.models.NetworkSuccess
import com.renault.domain.repositories.CarsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCarsRepository @Inject constructor
    (
    private val carService: CarsService,
    val carMapper: CarMapper
) : CarsRepository {
    override suspend fun getCars(nextPage: Int): NetworkResult<List<Car>> {
        return when (val carsResponse = carService.getCars(nextPage)) {
            is NetworkError -> {
                NetworkError(carsResponse.code, carsResponse.message)
            }

            is NetworkException -> {
                NetworkException(carsResponse.e)
            }

            is NetworkSuccess -> {
                NetworkSuccess(carMapper.fromListDto(carsResponse.data.Models))
            }
        }
    }

}