package com.renault.domaine.usecases

import com.renault.domaine.repositories.CarsRepository

class CarsUseCase(private val repository: CarsRepository)
{
    suspend fun invoke(nextPage: Int) = repository.getCars(nextPage)
}