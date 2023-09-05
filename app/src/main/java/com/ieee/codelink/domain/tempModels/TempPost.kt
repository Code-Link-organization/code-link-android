package com.ieee.codelink.domain.tempModels

data class TempPost(
    val userName : String,
    val date : String,
    val userImage:Int,
    val postImage:Int,
    val description : String,
    val likes:Int,
    val comments:Int,
    val shares:Int
)
