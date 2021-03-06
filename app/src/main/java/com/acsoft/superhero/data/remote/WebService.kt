package com.acsoft.superhero.data.remote

import com.acsoft.superhero.application.AppConstants
import com.acsoft.superhero.data.model.Hero
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface WebService {

    @GET("{id}")
    suspend fun getHeros(@Path("id") id:Int) : Hero

}

object RetrofitClient {

    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}