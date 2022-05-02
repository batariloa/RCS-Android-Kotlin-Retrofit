package com.example.rcs_androidremote.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
   @SerializedName("jwtToken")  val jwtToken:String
    )