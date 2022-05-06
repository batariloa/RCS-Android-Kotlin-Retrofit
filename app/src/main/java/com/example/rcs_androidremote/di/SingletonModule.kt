package com.example.rcs_androidremote.di

import android.content.Context
import android.content.SharedPreferences
import com.example.rcs_androidremote.api.*
import com.example.rcs_androidremote.models.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "http://10.0.2.2:8079/"
@Module
@InstallIn(SingletonComponent::class)
object SingletonModule  {


    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context):RCSApp{
        return app as RCSApp
    }

    @Singleton
    @Provides
    fun providePreferenceHelper(sharedPreferences: SharedPreferences): PreferenceHelper {
        return PreferenceHelper(sharedPreferences)
    }



    @Provides
    @Singleton
    fun provideStatusRepo(apiService: ApiService,preferenceHelper: PreferenceHelper): StatusRepository {
        return StatusRepository(apiService, preferenceHelper)
    }


    @Provides
    @Singleton
    fun provideUserRepo(apiService: ApiService,preferenceHelper: PreferenceHelper): UserRepository {
        return UserRepository(apiService, preferenceHelper)
    }
    @Provides
    @Singleton
    fun provideCommandRepo(preferenceHelper: PreferenceHelper,apiService: ApiService): CommandRepository {
        return CommandRepository(apiService, preferenceHelper)
    }



    @Provides
    fun provideLoggingInterceptor():HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel((HttpLoggingInterceptor.Level.BODY))    }

    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
       val httpClient = OkHttpClient.Builder()
       httpClient
           .connectTimeout(5, TimeUnit.SECONDS)
           .writeTimeout(2, TimeUnit.SECONDS)
           .readTimeout(3, TimeUnit.SECONDS)

        return httpClient.build()
    }

    @Provides
    fun provideConvectorFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetroFit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(apiService: ApiService): RetrofitClient {
        return RetrofitClient(apiService)
    }

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }


}