package com.example.rcs_androidremote.api

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.rcs_androidremote.models.GenericResponse
import com.example.rcs_androidremote.models.LoginResponse
import com.example.rcs_androidremote.models.MemoryStatus
import com.example.rcs_androidremote.models.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val  apiService: ApiService,
    private val preferenceHelper: PreferenceHelper
) {

    fun makeLoginCall(username:String, password:String, loggedIn:MutableLiveData<Boolean>) {


       val call: Call<LoginResponse> =  apiService.userLogin(User(username, password))
   call.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {


                if(response.isSuccessful){
                    loggedIn.postValue(true)
                    preferenceHelper.saveUser(response.body()?.jwtToken ?: "Unknown", username)
                    Log.d("Loggededed","Successful call ")
                }
                if(!response.isSuccessful){
                    Log.d("Loggededed","Failed call ")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

               })

    }


    fun logout() {
       preferenceHelper.logout()
    }




}