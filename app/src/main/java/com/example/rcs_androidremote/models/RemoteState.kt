package com.example.rcs_androidremote.models

data class RemoteState(
    var currentToken:String,
    var currentEmail:String,
    var currentStatus: MemoryStatus
                       )