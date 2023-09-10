package com.ieee.codelink.domain.tempModels

import com.ieee.codelink.data.fakeDataProvider.FakeDataProvider
import java.io.Serializable
import kotlin.random.Random

data class TempUserProfile(
    var name: String,
    var track: String,
    var image: Int,
    var followingCount: Int,
    var followersCount: Int,
    var likesCount: Int,
    var followers: List<TempUserSearch>,
    var posts: List<Int>
):Serializable


fun TempUserSearch.toTempUserProfile(): TempUserProfile {
    val user = this
    val fakeDataProvider = FakeDataProvider()

    val followersCount = Random.nextInt(40) + 20
    val followingCount = Random.nextInt(300) + 20
    val likesCount = Random.nextInt(300) + 20
    val numberOfPosts = Random.nextInt(11) + 1

    val followers = fakeDataProvider.getFakeUsers(followersCount)
    val posts = MutableList(numberOfPosts) {
        fakeDataProvider.getRandomImage()
    }

    return TempUserProfile(
        name = user.name,
        track = user.track,
        image = user.image,
        followingCount = followingCount,
        followersCount = followersCount,
        likesCount = likesCount,
        followers = followers,
        posts = posts
    )
}