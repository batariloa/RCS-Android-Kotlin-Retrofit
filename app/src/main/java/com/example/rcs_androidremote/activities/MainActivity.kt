package com.example.rcs_androidremote.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.rcs_androidremote.R
import com.example.rcs_androidremote.api.ApiService
import com.example.rcs_androidremote.api.RetrofitClient
import com.example.rcs_androidremote.models.LoginResponse
import com.example.rcs_androidremote.models.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val email = findViewById<EditText>(R.id.et_username)
        val password = findViewById<EditText>(R.id.et_password)
        val clickMe = findViewById<Button>(R.id.button_signin)
        val clickRegister = findViewById<TextView>(R.id.register_page_btn)

        clickRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }


       clickMe.setOnClickListener {

           val emailValue = email.text.toString().trim()
           val passwordValue = password.text.toString().trim()

           if (emailValue.isEmpty()) {
               email.error = "Email required"
               return@setOnClickListener
           }

           if (passwordValue.isEmpty()) {
               password.error = "Password required"
               return@setOnClickListener
           }

           println("Email je $emailValue a pass je $passwordValue")

           login(emailValue,passwordValue)



       }





    }


    private fun login(email:String, password:String){

        // launching a new coroutine
        val retroInstance = RetrofitClient.getRetroInstance().create(ApiService::class.java)
        val call = retroInstance.userLogin(User(email, password))
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
                        saveCurrentUser(response.body()?.jwtToken ?: "No token found", email)

                        val intent = Intent(this@MainActivity, RemoteActivity::class.java)
                        startActivity(intent)
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    println("Failure.")
                    Log.d("RetrofitTest", t.toString())
                }

            })

        }
    }

    fun saveCurrentUser(token:String, email:String){

        RetrofitClient.currentToken = "Bearer $token"
        RetrofitClient.currentUserEmail = email
    }


}