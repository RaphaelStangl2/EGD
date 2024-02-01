package com.example.egd.data.http

import com.example.egd.data.DateJsonAdapter
import com.example.egd.data.LocalDateJsonAdapter
import com.example.egd.data.dto.DateRangeDto
import com.example.egd.data.entities.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "https://student.cloud.htl-leonding.ac.at/r.alo/egd-user-with-quarkus/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    //.add(DateJsonAdapter())
    .add(LocalDateJsonAdapter())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface HttpApiService {
    @Headers("Content-Type: application/json")
    @POST("egd/drives")
    suspend fun addDrive(@Body drive: Drive): ResponseBody

    @Headers("Content-Type: application/json")
    @POST("egd/drives/getDrivesByDateRange")
    suspend fun getDrivesByCarBetweenDateRange(@Body dateRangeDto: DateRangeDto): Array<Drive>

    @Headers("Content-Type: application/json")
    @GET("egd/drives/{userCarId}")
    suspend fun getDrivesByUserCar(@Path ("userCarId") userCarId:Long): Array<Drive>

    @Headers("Content-Type: application/json")
    @POST("egd/drives/getAllCostsByDateRange")
    suspend fun getCostsByCarBetweenDateRange(@Body dateRangeDto: DateRangeDto): Array<Drive>

    @Headers("Content-Type: application/json")
    @GET("egd/drives/costsByUserId/{userCarId}")
    suspend fun getCostsByUserCar(@Path ("userCarId") userCarId:Long): Array<Drive>

    @Headers("Content-Type: application/json")
    @DELETE("egd/drives/{driveId}")
    suspend fun removeDrive(@Path ("driveId") driveId:Long): ResponseBody

    @Headers("Content-Type: application/json")
    @GET("egd/drives/{userCarId}")
    suspend fun getAllDrivesByUserCarId(@Path ("userCarId") userCarId: Long): ResponseBody

    @Headers("Content-Type: application/json")
    @PUT("egd/invitations")
    suspend fun updateInvitationStatus(@Body invitation:Invitation): ResponseBody

    @Headers("Content-Type: application/json")
    @GET("egd/invitations/byUserId/{invitationId}")
    suspend fun getInvitationByUserId(@Path("invitationId") invitationId:Long): ResponseBody?

    @Headers("Content-Type: application/json")
    @GET("egd/invitations/invsSendByUserId/{invitationId}")
    suspend fun getInvitationBySendUserId(@Path("invitationId") invitationId:Long): ResponseBody?

    @Headers("Content-Type: application/json")
    @POST("egd/invitations")
    suspend fun addInvitation(@Body invitation:Invitation): ResponseBody?

    @Headers("Content-Type: application/json")
    @POST("egd/userCar/removeUserCar")
    suspend fun deleteUserCar(@Body userCar:UserCar)

    @Headers("Content-Type: application/json")
    @PUT("egd/cars")
    suspend fun putCar(@Body car: Car) : ResponseBody

    @Headers("Content-Type: application/json")
    @DELETE("egd/cars/{carId}")
    suspend fun deleteCar(@Path("carId") carId:Long) : ResponseBody?

    @Headers("Content-Type:application/json")
    @PUT("egd/cars/addCurrentDriver")
    suspend fun putCurrentDriver(@Body userCar:UserCar) : ResponseBody

    @Headers("Content-Type:application/json")
    @PUT("egd/cars/removeCurrentDriver")
    suspend fun removeCurrentDriver(@Body userCar:UserCar) : ResponseBody

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
    @GET("egd/cars/carsByUserId/{userId}")
    suspend fun getCarsForUser(@Path("userId") userId: Long): ResponseBody

    @Headers("Content-Type: application/json")
    @GET("egd/users/{filter}")
    suspend fun getUserForFilter(@Path("filter") userId: String): ResponseBody

    @Headers("Content-Type: application/json")
    @GET("egd/users/email/{email}")
    suspend fun getUserForEmail(@Path("email") email: String): ResponseBody


    @Headers("Content-Type: application/json")
    @POST("egd/userCar/getUserCarWithOutId")
    suspend fun getUserCarWithoutId(@Body userCar: UserCar): UserCar

    @Headers("Content-Type: application/json")
    @DELETE("egd/invitations/{invId}")
    suspend fun deleteInvitation(@Path("invId") invitationId: String): Response<Unit>

    @Headers("Content-Type: application/json")
    @POST("egd/costs")
    suspend fun addCosts(@Body costs: Costs): ResponseBody
}

object HttpService {
    val retrofitService : HttpApiService by lazy {
        retrofit.create(HttpApiService::class.java) }
}