package com.example.rcs_androidremote.ui.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rcs_androidremote.api.CommandRepository
import com.example.rcs_androidremote.api.StatusRepository
import com.example.rcs_androidremote.api.UserRepository
import com.example.rcs_androidremote.models.MemoryStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemoteViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val statusRepository: StatusRepository,
    private val commandRepository: CommandRepository

)  :ViewModel() {

    private val _currentStatus= MutableLiveData<MemoryStatus>()
    val currentStatus:LiveData<MemoryStatus>
        get() = _currentStatus

    fun logout(){
        userRepository.logout()

    }


     fun callShutdown(){
       commandRepository.callShutDown()
    }

     fun callMonkey(){
        commandRepository.callMonkey()
    }

    fun callTorrent(link:String){
        commandRepository.callTorrent(link)
    }
    fun callTerminal(command:String){
        commandRepository.callTerminal(command)
    }


    fun getStatus(){
        return statusRepository.callGetStatus(_currentStatus)
    }
}