package com.example.egd.data.http

import com.example.egd.data.entities.Car
import com.example.egd.data.entities.User
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import javax.inject.Inject

private const val BASE_URL =
    "https://student.cloud.htl-leonding.ac.at/b.kadir/egd-user-with-quarkus/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface HttpApiService {
    @Headers("Content-Type: application/json")
    @POST("egd/users/registration/")
    suspend fun postRegistration(@Body user: User): ResponseBody

    @Headers("Content-Type: application/json")
    @POST("egd/users/login/")
    suspend fun postLogin(@Body user: User): ResponseBody

    @Headers("Content-Type: application/json")
    @GET("egd/cars/{userId}")
    suspend fun getCarsForUser(@Path("userId") userId: Long): ResponseBody

    @Headers("Content-Type: application/json")
    @GET("egd/users/{filter}")
    suspend fun getUserForFilter(@Path("filter") userId: String): ResponseBody
}

object HttpService {
    val retrofitService : HttpApiService by lazy {
        retrofit.create(HttpApiService::class.java) }
}