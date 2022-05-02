package com.example.rcs_androidremote.api



import com.example.rcs_androidremote.models.LoginResponse
import com.example.rcs_androidremote.models.GenericResponse
import com.example.rcs_androidremote.models.MemoryStatus
import com.example.rcs_androidremote.models.User
import retrofit2.Call
import retrofit2.http.*


interface ApiService {


    @POST("register")
    fun createUser(@Body params:User): Call<GenericResponse>


    @POST("authenticate")
    fun userLogin(@Body params:User):Call<LoginResponse>

    @POST("monkey")
    @FormUrlEncoded
    fun addMonkey(@Header("Authorization") token: String, @Field("username") email:String):Call<GenericResponse>

    @POST("terminal")
    @FormUrlEncoded
    fun sendCommand(@Header("Authorization") token: String,
                    @Field("username") email:String,
                    @Field("command") command:String ):Call<GenericResponse>

    @POST("torrent")
    @FormUrlEncoded
    fun addTorrent(@Header("Authorization") token: String,
                    @Field("username") email:String,
                    @Field("magnetLink") link:String ):Call<GenericResponse>

    @POST("shutdown")
    @FormUrlEncoded
    fun sendShutdownSignal(@Header("Authorization") token: String,
                   @Field("username") email:String):Call<GenericResponse>


    @GET("status")
    fun getStatus(@Header("Authorization") token: String,
                  @Query(value="username", encoded=true)email:String):Call<MemoryStatus>
}