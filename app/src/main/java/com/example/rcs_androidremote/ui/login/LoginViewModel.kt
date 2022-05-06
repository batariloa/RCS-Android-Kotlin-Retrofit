package com.example.rcs_androidremote.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rcs_androidremote.api.ApiService
import com.example.rcs_androidremote.api.RetrofitClient
import com.example.rcs_androidremote.api.UserRepository
import com.example.rcs_androidremote.models.LoginResponse
import com.example.rcs_androidremote.models.MemoryStatus
import com.example.rcs_androidremote.models.RemoteState
import com.example.rcs_androidremote.models.User
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class LoginViewModel
 @Inject constructor(
private val userRepository: UserRepository
) :  ViewModel() {

    val loggedIn= MutableLiveData(false)



    fun login(emailValue:String, passwordValue:String){

        println("OBSERVABLE VALUE "+ loggedIn.value)

        println("Email je $emailValue a pass je $passwordValue")


        userRepository.makeLoginCall(emailValue,passwordValue, loggedIn)

    }
}