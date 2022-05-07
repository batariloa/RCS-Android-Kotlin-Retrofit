package com.example.rcs_androidremote.ui.remote

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.rcs_androidremote.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RemoteActivity : AppCompatActivity() {



    private val viewModel: RemoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote)


        val etCommand = findViewById<TextView>(R.id.et_terminal)
        val etLink = findViewById<TextView>(R.id.et_torrent)


        val monkey = findViewById<Button>(R.id.button_monkey)
        val terminal = findViewById<Button>(R.id.button_terminal)
        val torrent = findViewById<Button>(R.id.button_torrent)
        val shutdown = findViewById<Button>(R.id.button_shut_down)
        val logout = findViewById<TextView>(R.id.button_logout)

        monkey.setOnClickListener {
            viewModel.callMonkey()
            println("PRITISKAM MONKEy")
        }

        terminal.setOnClickListener {
            viewModel.callTerminal(etCommand.text.toString())
        }

        torrent.setOnClickListener {
            viewModel.callTorrent(etLink.text.toString())
        }

        shutdown.setOnClickListener {
            viewModel.callShutdown()
        }

        logout.setOnClickListener {
            viewModel.logout()
        }

        val diskSpaceTotal = findViewById<TextView>(R.id.disk_all)
        viewModel.currentStatus.observe(
            this,
            Observer {
                diskSpaceTotal.text = it.diskSpaceTotal.toString()
            }
        )


            GlobalScope.launch {
                while(isActive){
                    viewModel.getStatus()
                    delay(2000)
                }
            }



    }




}