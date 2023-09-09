package com.ieee.codelink.domain.tempModels

data class TempUserSearch(
    val name : String,
    val image : Int,
    val track : String,
    val isFollowed : Boolean = false
)
