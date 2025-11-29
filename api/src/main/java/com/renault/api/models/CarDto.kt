package com.renault.api.models

data class CarDto(
    val model_name: String, val model_make_id: String,
    val imageUrl: String = ""
)
