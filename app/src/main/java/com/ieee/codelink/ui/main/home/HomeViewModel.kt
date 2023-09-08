package com.ieee.codelink.ui.main.home

import android.content.Context
import com.ieee.codelink.R
import com.ieee.codelink.common.cacheImageToFile
import com.ieee.codelink.common.createMultipartBodyPartFromFile
import com.ieee.codelink.common.getImageFileFromRealPath
import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.core.isSuccess
import com.ieee.codelink.data.repository.PostsRepository
import com.ieee.codelink.data.repository.UserRepository
import com.ieee.codelink.domain.models.CreatePostModel
import com.ieee.codelink.domain.models.Post
import com.ieee.codelink.domain.models.PostsResponseData
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.domain.models.responses.PostsResponse
import com.ieee.codelink.domain.tempModels.TempUserStory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    private val userRepository: UserRepository,
    private val context: Context
) : BaseViewModel() {

    val postsRequestState: MutableStateFlow<ResponseState<PostsResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val createPostsRequestState: MutableStateFlow<ResponseState<BaseResponse>> =
        MutableStateFlow(ResponseState.Empty())


    private var postsList : List<Post>? = null

    suspend fun getHomePosts() {
        postsRequestState.value = ResponseState.Loading()
        val response = postsRepository.getPosts()
        postsRequestState.value = handleResponse(response)
        saveNewPosts()
    }

    fun loadPosts() {
        val postsResponse = PostsResponse(true , PostsResponseData(postsList!!))
        postsRequestState.value = ResponseState.Success(postsResponse)
    }
    private fun saveNewPosts() {
        if (postsRequestState.value.isSuccess){
            postsRequestState.value.data?.let {
                postsList =  it.data.postData
            }
        }
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


   suspend fun createPost(createPostModel: CreatePostModel){
       val imgPart = if (createPostModel.images !=null){
           val path = cacheImageToFile( context , createPostModel.images[0])
           val file = getImageFileFromRealPath(path)
            createMultipartBodyPartFromFile(file , "file_path")
       } else{
           null
       }
       createPostsRequestState.value = ResponseState.Loading()
       val response = postsRepository.createPost(createPostModel.content, imgPart)
       createPostsRequestState.value = handleResponse(response)
   }

    fun isFirstCall(): Boolean = postsList == null

    fun getUser(): User = userRepository.getCachedUser()

    suspend fun likePost(post: Post): Boolean? {
        val response = postsRepository.likePost(postId = post.id)
        val responseState = handleResponse(response)
        return if (responseState.isSuccess) {
            val message = responseState.data!!.message
            message != "Post unliked successfully"
        } else
            null
    }


}


