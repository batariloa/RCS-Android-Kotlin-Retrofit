package com.example.rcs_androidremote.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rcs_androidremote.api.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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