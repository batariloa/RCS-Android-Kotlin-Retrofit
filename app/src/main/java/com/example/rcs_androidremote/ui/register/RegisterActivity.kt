package com.example.rcs_androidremote.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.rcs_androidremote.R
import com.example.rcs_androidremote.api.ApiService
import com.example.rcs_androidremote.api.RetrofitClient
import com.example.rcs_androidremote.models.User
import com.example.rcs_androidremote.ui.login.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val emailField = findViewById<EditText>(R.id.et_email_register)
        val passwordField = findViewById<EditText>(R.id.et_password_register)
        val passwordAgainField = findViewById<EditText>(R.id.et_password_again_register)
        val registerButton = findViewById<Button>(R.id.button_register)
        val loginButton = findViewById<TextView>(R.id.login_page_btn)

        loginButton.setOnClickListener {
            //ovde treba logout
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

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


        }}



}