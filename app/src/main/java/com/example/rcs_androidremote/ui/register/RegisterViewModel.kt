package com.example.rcs_androidremote.ui.register

import androidx.lifecycle.ViewModel
import com.example.rcs_androidremote.api.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
    private val userRepository: UserRepository
) :ViewModel(){
}