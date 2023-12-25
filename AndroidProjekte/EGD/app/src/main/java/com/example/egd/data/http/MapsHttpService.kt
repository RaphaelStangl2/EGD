package com.example.egd.data.http

import com.example.egd.data.DateJsonAdapter
import com.example.egd.data.entities.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "https://maps.googleapis.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()).add(DateJsonAdapter())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface MapsHttpApiService {
    @Headers("Content-Type: application/json")
    @GET("/maps/api/directions/json")
    suspend fun getMapsDirections(@Query("origin") origin: String,@Query("destination") destination:String, @Query("mode") mode:String, @Query("key") key:String) : DirectionsResponse

}


object MapsHttpService {
    val retrofitService : MapsHttpApiService by lazy {
        retrofit.create(MapsHttpApiService::class.java) }
}