package com.example.jumatexpress

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

//data class User(val name: String, val email: String, val password: String)

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @POST("users")
    suspend fun addUser(@Body user: User): Response<User>

    @POST("login") // Replace "login" with your actual login endpoint
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/logbooks")
    suspend fun getLogbooks(
        @Query("id_mahasiswa") idMahasiswa: Int
    ): Response<List<Logbook>>


}