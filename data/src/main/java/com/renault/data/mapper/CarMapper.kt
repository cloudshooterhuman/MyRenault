package com.renault.data.mapper

import com.renault.api.models.CarDto
import com.renault.domain.models.Car
import javax.inject.Inject


class CarMapper@Inject constructor(){
    fun fromListDto(carsDto: List<CarDto>): List<Car> =
        carsDto.map { fromDto(it) }

    private fun fromDto(it: CarDto) = Car (id = it.id,
        title = it.title, description = it.description, imageUrl = it.imageUrl)
}