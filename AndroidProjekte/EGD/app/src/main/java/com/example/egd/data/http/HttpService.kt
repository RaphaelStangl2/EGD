package com.example.egd.data.http

import com.example.egd.data.entities.User
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Inject

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface HttpApiService {
    @Headers("Content-Type: application/json")
    @POST("/egd/users/registration")
    suspend fun postRegistration(@Body user: User): Response

    @Headers("Content-Type: application/json")
    @POST("/egd/users/login")
    suspend fun postLogin(@Body user: User): Response
}

object HttpService {
    val retrofitService : HttpApiService by lazy {
        retrofit.create(HttpApiService::class.java) }
}