package com.renault.api.models

class Page<T>(
    val data: List<T>,
    val total: UInt,
)