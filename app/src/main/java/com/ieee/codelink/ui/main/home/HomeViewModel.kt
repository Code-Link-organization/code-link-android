package com.ieee.codelink.ui.main.home

import com.ieee.codelink.R
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.repository.PostsRepository
import com.ieee.codelink.domain.models.responses.PostsResponse
import com.ieee.codelink.domain.tempModels.TempUserStory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : BaseViewModel() {

    val postsRequestState: MutableStateFlow<ResponseState<PostsResponse>> =
        MutableStateFlow(ResponseState.Empty())


    suspend fun getHomePosts() {
        postsRequestState.value = ResponseState.Loading()
        val response = postsRepository.getPosts()
        postsRequestState.value = handleResponse(response)
    }


    fun getFakeStories(): ArrayList<TempUserStory> {
        val list = ArrayList<TempUserStory>()
        var randomInt = Random.nextInt(3, 10)
        repeat(randomInt) {
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