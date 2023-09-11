package com.ieee.codelink.domain.tempModels

import kotlin.random.Random

data class TempUserSearch(
    val name: String,
    val image: Int,
    var track: String,
    val isFollowed: Boolean = false
)

fun TempUserSearch.toMentor(): TempMentor {
    val num1 : Int = Random.nextInt(4)+5
    val num2 : Int = Random.nextInt(4)+5
    return TempMentor(
        name,
        image,
        track,
        (Random.nextInt(40)+10).toDouble(),
        "${num1}Am to ${num1+3}Am",
        "${num2}Pm to ${num2+3}Pm"
    )
}