package com.example.rcs_androidremote.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.rcs_androidremote.R
import com.example.rcs_androidremote.api.ApiService
import com.example.rcs_androidremote.api.RetrofitClient
import kotlinx.coroutines.*

class RemoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote)



        updateStatus()  //update status and show in UI

        val monkey = findViewById<Button>(R.id.button_monkey)
        val terminal = findViewById<Button>(R.id.button_terminal)
        val torrent = findViewById<Button>(R.id.button_torrent)
        val shutdown = findViewById<Button>(R.id.button_shut_down)
        val logout = findViewById<TextView>(R.id.button_logout)

        monkey.setOnClickListener{ callMonkey() }
        terminal.setOnClickListener { callTerminal() }
        torrent.setOnClickListener { callTorrent() }
        shutdown.setOnClickListener { callShutdown() }
        logout.setOnClickListener { logout() }




    }



    private fun logout(){
        RetrofitClient.logout()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }


    private fun callShutdown(){
        val retroInstance = RetrofitClient.getRetroInstance().create(ApiService::class.java)
        val call = retroInstance.sendShutdownSignal(RetrofitClient.currentToken,RetrofitClient.currentUserEmail)
        RetrofitClient.makeCall(call)
    }

    private fun callMonkey(){
        val retroInstance = RetrofitClient.getRetroInstance().create(ApiService::class.java)
        val call = retroInstance.addMonkey(RetrofitClient.currentToken,RetrofitClient.currentUserEmail)
        RetrofitClient.makeCall(call)
    }

    private fun callTorrent(){
        val torrent = findViewById<EditText>(R.id.et_torrent).text.toString()
        val retroInstance = RetrofitClient.getRetroInstance().create(ApiService::class.java)
        val call = retroInstance.addTorrent(RetrofitClient.currentToken,  RetrofitClient.currentUserEmail, torrent)
        RetrofitClient.makeCall(call)
    }
    private fun callTerminal(){
        val command = findViewById<EditText>(R.id.et_terminal).text.toString()
        val retroInstance = RetrofitClient.getRetroInstance().create(ApiService::class.java)
        val call = retroInstance.sendCommand(RetrofitClient.currentToken,  RetrofitClient.currentUserEmail, command)
        RetrofitClient.makeCall(call)
    }

    private fun updateStatus(){
        val diskSpaceTotal = findViewById<TextView>(R.id.disk_all)

        GlobalScope.launch {

                RetrofitClient.getStatus()
                while(isActive) {

                    withContext(Dispatchers.Main) {
                        diskSpaceTotal.text = RetrofitClient.currentStatus.diskSpaceTotal.toString()
                    }
                    delay(1000)
                }

        }
    }

}