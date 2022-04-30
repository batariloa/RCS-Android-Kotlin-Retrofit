package com.example.rcs_androidremote.api



import com.example.rcs_androidremote.models.LoginResponse
import com.example.rcs_androidremote.models.RegisterResponse
import com.example.rcs_androidremote.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {


    @POST("register")
    fun createUser(@Body params:User): Call<RegisterResponse>


    @POST("authenticate")
    fun userLogin(@Body params:User):Call<LoginResponse>
}