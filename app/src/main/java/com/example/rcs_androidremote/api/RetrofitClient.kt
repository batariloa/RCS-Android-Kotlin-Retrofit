package com.example.rcs_androidremote.api


import android.util.Log
import com.example.rcs_androidremote.models.*
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject



@Module
@InstallIn(SingletonComponent::class)
 class RetrofitClient
@Inject constructor(private val apiService: ApiService)
  {

   var currentToken: String = ""
   var currentUserEmail: String = ""
   var currentStatus:MemoryStatus = MemoryStatus("Not yet available..", 0 , 0)

   fun logout() {
    currentToken = ""
   }

   fun login(email:String, password:String): Boolean {

    var success= false

    val call = apiService.userLogin(User(email, password))
    GlobalScope.launch {
     call.enqueue(object : Callback<LoginResponse> {
      override fun onResponse(
       call: Call<LoginResponse>,
       response: Response<LoginResponse>
      ) {
       println(
        "Success: ${response.body()}"
       )
       if (response.isSuccessful) {
        println("Successful"+ response.body().toString())

       success = true

       }

      }

      override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
       println("Failure.")
       Log.d("RetrofitTest", t.toString())
      }

     })

    }
    return success
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

    val call = apiService.getStatus(currentToken,currentUserEmail)

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