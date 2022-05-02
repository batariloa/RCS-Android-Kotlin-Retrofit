package com.example.rcs_androidremote.models

data class MemoryStatus(
    val status:String,
    val diskSpaceUsable:Long,
    var diskSpaceTotal:Long
)
