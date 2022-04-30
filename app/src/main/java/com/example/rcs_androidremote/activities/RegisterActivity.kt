package com.example.rcs_androidremote.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.rcs_androidremote.R
import com.example.rcs_androidremote.api.ApiService
import com.example.rcs_androidremote.api.RetrofitClient
import com.example.rcs_androidremote.models.RegisterResponse
import com.example.rcs_androidremote.models.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val emailField = findViewById<EditText>(R.id.et_register_email)
        val passwordField = findViewById<EditText>(R.id.et_register_password)
        val passwordAgainField = findViewById<EditText>(R.id.et_register_pass_again)

        val registerButton = findViewById<Button>(R.id.btn_submit_register)

        registerButton.setOnClickListener{

            val emailValue = emailField.text.toString().trim()
            val passwordValue = passwordField.text.toString().trim()
            val passwordAgainValue = passwordAgainField.text.toString().trim()
            if (emailValue.isEmpty()) {
                emailField.error = "Email required"
                return@setOnClickListener
            }

            if (passwordValue.isEmpty()) {
                passwordField.error = "Password required"
                return@setOnClickListener
            }

            if (passwordAgainValue != passwordValue) {
                passwordAgainField.error = "Please enter your password again"
                return@setOnClickListener
            }

            println("Email je $emailValue a pass je $passwordValue")
            val retroInstance = RetrofitClient.getRetroInstance().create(ApiService::class.java)
            val call = retroInstance.createUser(User(emailValue, passwordValue))

            // launching a new coroutine
            GlobalScope.launch {
                //calling API
                call.enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        println(
                            "Succeeded ${response.body()} "
                        )
                        if (response.isSuccessful)
                            println(response.body().toString())
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        println("Authentication failed")
                        Log.d("RetrofitTest", t.toString())
                    }

                })

            }

        }}



}