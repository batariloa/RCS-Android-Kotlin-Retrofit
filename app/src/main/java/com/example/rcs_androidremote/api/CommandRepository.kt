package com.example.rcs_androidremote.api

import android.util.Log
import com.example.rcs_androidremote.models.GenericResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CommandRepository  @Inject constructor(
    private val  apiService: ApiService,
    private val preferenceHelper: PreferenceHelper
){

    fun callShutDown() {
        val call: Call<GenericResponse> = apiService.sendShutdownSignal(
            preferenceHelper.getToken() ?: "",
            preferenceHelper.getEmail() ?: "")
        makeCommandCall(call)
    }

    fun callMonkey() {

        val call: Call<GenericResponse> = apiService.addMonkey(
            preferenceHelper.getToken() ?: "",
            preferenceHelper.getEmail() ?: "")
        makeCommandCall(call)
    }

    fun callTorrent(link:String) {

        val call: Call<GenericResponse> = apiService.sendCommand(
            preferenceHelper.getToken() ?: "",
            preferenceHelper.getEmail() ?: "",link)
        makeCommandCall(call)
    }

    fun callTerminal(command:String) {
        val call: Call<GenericResponse> = apiService.sendCommand(
            preferenceHelper.getToken() ?: "",
            preferenceHelper.getEmail() ?: "",command)
        makeCommandCall(call)
    }



    private fun makeCommandCall(call: Call<GenericResponse>) {


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