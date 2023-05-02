package com.example.practice.users.data.remote.api

import com.example.practice.users.data.remote.model.UserResponse
import retrofit2.http.GET

interface UserApi {

    @GET("/users")
    suspend fun getUsers():List<UserResponse>
}