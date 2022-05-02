package com.example.rcs_androidremote.api


import android.util.Log
import com.example.rcs_androidremote.models.GenericResponse
import com.example.rcs_androidremote.models.MemoryStatus
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {

    companion object {


        var currentToken: String = ""
        var currentUserEmail: String = ""
        var currentStatus:MemoryStatus = MemoryStatus("Not yet available..", 0 , 0)
        private const val BASE_URL = "http://10.0.2.2:8079/"

        fun getRetroInstance(): Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()

                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
            client.addInterceptor(logging)

           return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun logout() {
            currentToken = ""
        }

        fun makeCall(call: Call<GenericResponse>){
            GlobalScope.launch {
                call.enqueue(object : Callback<GenericResponse> {
                    override fun onResponse(
                        call: Call<GenericResponse>,
                        response: Response<GenericResponse>
                    ) {
                        println(
                            "Success: ${response.body()}"
                        )
                        if (response.isSuccessful) {
                            println(response.body().toString())

                        }

                    }

                    override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                        println("Failure.")
                        Log.d("RetrofitTest", t.toString())
                    }
                })
            }
        }


         fun getStatus() {
             val retroInstance = getRetroInstance().create(ApiService::class.java)
             val call = retroInstance.getStatus(currentToken,currentUserEmail)

                 call.enqueue(object : Callback<MemoryStatus> {
                     override fun onResponse(
                         call: Call<MemoryStatus>,
                         response: Response<MemoryStatus>
                     ) {

                         println(
                             "Success: ${response.body()}"
                         )
                         if (response.isSuccessful) {
                             println(response.body().toString())

                                     currentStatus = MemoryStatus(
                                         response.body()?.status ?: "Not found",
                                         response.body()?.diskSpaceTotal ?: 0,
                                         response.body()?.diskSpaceUsable ?: 0

                                     )
                             println("Received: $currentStatus" )
                         }

                     }

                     override fun onFailure(call: Call<MemoryStatus>, t: Throwable) {
                         println("Failure.")
                         Log.d("RetrofitTest", t.toString())
                     }
                 })

         }

    }

}