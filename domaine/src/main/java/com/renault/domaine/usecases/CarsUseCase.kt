package com.renault.domain.usecases

import com.renault.domain.repositories.CarsRepository
import javax.inject.Inject

class CarsUseCase  @Inject constructor(private val repository: CarsRepository)
{
    suspend fun invoke(nextPage: Int) = repository.getCars(nextPage)
}