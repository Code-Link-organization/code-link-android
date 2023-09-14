package com.ieee.codelink.domain.models.responseData

import com.ieee.codelink.data.fakeDataProvider.FakeDataProvider
import com.ieee.codelink.domain.tempModels.TempUserSearch
import com.ieee.codelink.domain.tempModels.toTempUserProfile

data class LikeData(
    var id: Int,
    var user_id: Int,
    var post_id: Int,
    var created_at: String,
    var updated_at: String,
    var user_name: String,
    var user_imageUrl: String
)

fun LikeData.toTempUserSearch(): TempUserSearch {
    val dataProvider = FakeDataProvider()
    return TempUserSearch(
        user_name,
        dataProvider.getRandomManImage(),
        dataProvider.getRandomTrack()
    )
}

fun LikeData.toProfileUser() = this.toTempUserSearch().toTempUserProfile()
