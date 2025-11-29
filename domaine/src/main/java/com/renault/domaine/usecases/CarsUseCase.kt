package com.renault.domain.usecases

import com.renault.domain.repositories.CarsRepository

class CarsUseCase(private val repository: CarsRepository)
{
    suspend fun invoke(nextPage: Int) = repository.getCars(nextPage)
}