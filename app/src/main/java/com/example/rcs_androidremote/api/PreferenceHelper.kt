package com.example.rcs_androidremote.api

import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceHelper @Inject constructor(

    private val sharedPreferences: SharedPreferences
) {

    fun getToken(): String {
        return "Bearer " + sharedPreferences.getString("key_jwt", "Unknown")
    }
    fun getEmail(): String {
        return  sharedPreferences.getString("key_username", "Unknown")?:"Unknown"
    }
    fun saveUser(token:String, username:String) {
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString(KEY_AUTH,token)
        editor.putString(KEY_USERNAME,username)
        editor.apply()
        editor.commit()

    }
    fun logout(){
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString(KEY_AUTH,"")
        editor.putString(KEY_USERNAME,"")
        editor.apply()
        editor.commit()
    }

    companion object {
        private const val KEY_AUTH = "key_jwt"
        private const val KEY_USERNAME = "key_username"
    }

}