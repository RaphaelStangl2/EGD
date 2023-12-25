package com.example.egd.data.http

import com.example.egd.data.entities.Car
import com.example.egd.data.entities.User
import com.example.egd.data.entities.UserCar
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "https://student.cloud.htl-leonding.ac.at/r.alo/egd-user-with-quarkus/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface HttpApiService {
    @Headers("Content-Type: application/json")
    @DELETE("egd/userCar/{userCarId}")
    suspend fun deleteUserCar(@Path("userCarId") userCarId:Long?): ResponseBody

    @Headers("Content-Type: application/json")
    @PUT("egd/cars")
    suspend fun putCar(@Body car: Car) : ResponseBody

    @Headers("Content-Type: application/json")
    @GET("egd/users/getUsersForCar/{carId}")
    suspend fun getUsersForCar(@Path("carId") carId: Long?):ResponseBody

    @Headers("Content-Type: application/json")
    @POST("egd/users/registration/")
    suspend fun postRegistration(@Body user: User): ResponseBody

    @Headers("Content-Type: application/json")
    @POST("egd/cars/")
    suspend fun postCar(@Body car: Car): ResponseBody

    @Headers("Content-Type: application/json")
    @POST("egd/userCar/")
    suspend fun postUserCar(@Body userCarList: UserCar): ResponseBody

    @Headers("Content-Type: application/json")
    @POST("egd/userCar/addUserCarsList")
    suspend fun postUserCars(@Body userCarList: Array<UserCar>): ResponseBody

    @Headers("Content-Type: application/json")
    @POST("egd/users/login/")
    suspend fun postLogin(@Body user: User): ResponseBody

    @Headers("Content-Type: application/json")
    @GET("egd/cars/{userId}")
    suspend fun getCarsForUser(@Path("userId") userId: Long): ResponseBody

    @Headers("Content-Type: application/json")
    @GET("egd/users/{filter}")
    suspend fun getUserForFilter(@Path("filter") userId: String): ResponseBody

    @Headers("Content-Type: application/json")
    @GET("egd/users/email/{email}")
    suspend fun getUserForEmail(@Path("email") email: String): ResponseBody

}

object HttpService {
    val retrofitService : HttpApiService by lazy {
        retrofit.create(HttpApiService::class.java) }
}