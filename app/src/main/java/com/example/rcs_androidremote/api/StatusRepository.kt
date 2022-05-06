package com.example.rcs_androidremote.api

import androidx.lifecycle.MutableLiveData
import com.example.rcs_androidremote.models.MemoryStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class StatusRepository
@Inject constructor(
    private val apiService: ApiService,
    private val preferenceHelper: PreferenceHelper

)
{
    fun callGetStatus(status: MutableLiveData<MemoryStatus>){
        val email = preferenceHelper.getEmail()
        val jwt = preferenceHelper.getToken()
        if (email != null && jwt != null) {
            val call: Call<MemoryStatus> = apiService.getStatus(jwt, email)

            call.enqueue(object : Callback<MemoryStatus> {
                override fun onResponse(
                    call: Call<MemoryStatus>,
                    response: Response<MemoryStatus>
                ) {
                    if (response.isSuccessful) {
                        println(response.body().toString())

                        val responseStatus = MemoryStatus(
                            response.body()?.status ?: "Not found",
                            response.body()?.diskSpaceTotal ?: 0,
                            response.body()?.diskSpaceUsable ?: 0

                        )
                        status.postValue(responseStatus)

                        println("Received: $responseStatus" )
                    }
                }

                override fun onFailure(call: Call<MemoryStatus>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

}





