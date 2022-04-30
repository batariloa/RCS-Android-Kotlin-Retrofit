package com.example.rcs_androidremote.api


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {

    companion object {

        private const val BASE_URL = "http://10.0.2.2:8079/"

        fun getRetroInstance(): Retrofit {
            var logging = HttpLoggingInterceptor()
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
    }

}