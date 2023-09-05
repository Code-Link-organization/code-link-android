package com.ieee.codelink.ui.main.home

import com.ieee.codelink.R
import com.ieee.codelink.common.createRandomString
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.domain.tempModels.TempPost
import com.ieee.codelink.domain.tempModels.TempUserStory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    fun getFakePosts(): ArrayList<TempPost> {
        val list = ArrayList<TempPost>()
        for (i in 0 .. 20){
            list.add(generateRandomPost())
        }
       return list
    }

   private fun generateRandomPost(): TempPost = TempPost(
        userName = createRandomString(12),
        date = "yesterday",
        userImage = R.drawable.ic_onboarding_1,
        postImage = R.drawable.ic_onboarding_2,
        description = createRandomString(42),
        500,
        200,
        100
    )

    fun getFakeStories() : ArrayList<TempUserStory>{
        val list = ArrayList<TempUserStory>()
        var randomInt = Random.nextInt(3, 10)
        repeat(randomInt){
            list.add(generateRandomStory())
        }
        return list
    }


    private fun generateRandomStory():TempUserStory{
        var randomInt = Random.nextInt(0, 3)
        val thumb = when(randomInt){
            0->{
                R.drawable.ic_onboarding_1
            }
            1->{
                R.drawable.ic_onboarding_2

            }
            else->{
                R.drawable.ic_onboarding_3
            }
        }
        randomInt = Random.nextInt(0, 3)

        val userImage = when(randomInt){
            0->{
                R.drawable.ic_onboarding_1
            }
            1->{
                R.drawable.ic_onboarding_2

            }
            else->{
                R.drawable.ic_onboarding_3
            }
        }

        return TempUserStory(thumb, userImage)

    }
}