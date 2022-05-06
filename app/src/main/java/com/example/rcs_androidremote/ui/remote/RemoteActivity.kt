package com.example.rcs_androidremote.ui.remote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.rcs_androidremote.R
import com.example.rcs_androidremote.api.ApiService
import com.example.rcs_androidremote.api.RetrofitClient
import com.example.rcs_androidremote.ui.login.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class RemoteActivity : AppCompatActivity() {



    private val viewModel: RemoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote)




        val monkey = findViewById<Button>(R.id.button_monkey)
        val terminal = findViewById<Button>(R.id.button_terminal)
        val torrent = findViewById<Button>(R.id.button_torrent)
        val shutdown = findViewById<Button>(R.id.button_shut_down)
        val logout = findViewById<TextView>(R.id.button_logout)

        monkey.setOnClickListener {
            viewModel.callMonkey()
            println("PRITISKAM MONKEy")
        }

        val diskSpaceTotal = findViewById<TextView>(R.id.disk_all)
        viewModel.currentStatus.observe(this, Observer {
            diskSpaceTotal.text = it.diskSpaceTotal.toString()
        })


            GlobalScope.launch {
                while(isActive){
                    viewModel.getStatus()
                    delay(2000)
                }
            }



    }   private fun updateStatus(){



        }




}