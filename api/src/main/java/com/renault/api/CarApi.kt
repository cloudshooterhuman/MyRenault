package com.renault.api

import com.renault.api.adapters.NetworkResultCallAdapterFactory
import com.renault.api.services.CarsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CarApi @Inject constructor() {
    companion object {
        const val API_URL = "https://www.carqueryapi.com/api/0.3/?cmd=getModels&make=ford"
    }

    val carsService: CarsService
    private val interceptor = HttpLoggingInterceptor()

    init {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(OkHttpClient.Builder().addInterceptor(interceptor).build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .build()

        carsService = retrofit.create(CarsService::class.java)
    }
}