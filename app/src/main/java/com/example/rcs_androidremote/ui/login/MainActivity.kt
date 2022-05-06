package com.example.rcs_androidremote.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.rcs_androidremote.R
import com.example.rcs_androidremote.ui.remote.RemoteActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {



     private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val email = findViewById<EditText>(R.id.et_username)
        val password = findViewById<EditText>(R.id.et_password)
        val clickMe = findViewById<Button>(R.id.button_signin)
        val clickRegister = findViewById<TextView>(R.id.register_page_btn)

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

            viewModel.login(emailValue, passwordValue)

        }

        //show new screen if login is successful
        viewModel.loggedIn.observe(this, Observer {
       if(it) {
           val intent = Intent(this, RemoteActivity::class.java)
           intent.putExtra("SHOW_WELCOME", true)
           startActivity(intent)
           finish()
       }
        })

        }






    }




