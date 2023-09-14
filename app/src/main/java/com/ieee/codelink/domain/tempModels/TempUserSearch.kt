package com.ieee.codelink.domain.tempModels

import com.ieee.codelink.data.fakeDataProvider.FakeDataProvider
import kotlin.random.Random

data class TempUserSearch(
    val name: String,
    val image: Int,
    var track: String,
    val isFollowed: Boolean = false,
    val isMan: Boolean = false,
    var id :Int = 1
)

fun TempUserSearch.toMentor(): TempMentor {
    val fakeDataProvider = FakeDataProvider()
    val img = if (isMan) {
        fakeDataProvider.getRandomManMentorImage()
    } else {
        fakeDataProvider.getRandomGirlMentorImage()
    }
    return TempMentor(
        "Eng/$name",
        img,
        track,
        "${(Random.nextInt(40) + 10).toDouble()}$"
    )
}