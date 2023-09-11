package com.ieee.codelink.domain.tempModels

import java.io.Serializable

data class TempMentor (
    var name: String,
    var image : Int,
    var track : String,
    var price : String,
    var rate : Double = 4.0
):Serializable