package com.ieee.codelink.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentHomeBinding
import com.ieee.codelink.domain.CreatePostModel
import com.ieee.codelink.domain.models.Post
import com.ieee.codelink.domain.models.responses.PostsResponse
import com.ieee.codelink.ui.adapters.PostsAdapter
import com.ieee.codelink.ui.adapters.StoriesAdapter
import com.ieee.codelink.ui.dialogs.CreatePostDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var storiesAdapter: StoriesAdapter
    private lateinit var createPostDialog: CreatePostDialogFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callData()
        //setStoriesRV()
        setOnClicks()
        setObservers()
    }

    private fun setOnClicks() {
        binding.frameSearch.setOnClickListener {
            createPostDialog = CreatePostDialogFragment(createPost)
            createPostDialog.show(childFragmentManager, "create_post")
        }
    }

    private fun callData() {
        lifecycleScope.launch {
            viewModel.getHomePosts()
        }
    }

    private fun setObservers() {
        viewModel.postsRequestState.awareCollect { state ->
            postsObserver(state)
        }

        viewModel.createPostsRequestState.awareCollect { state ->
            createPostsObserver(state)
        }

    }

    private fun createPostsObserver(state: ResponseState<BaseResponse>) {
        when (state) {
            is ResponseState.Empty,
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
            }

            is ResponseState.NetworkError -> {
                showToast(getString(R.string.network_error))
            }

            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.createPostsRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
            }

            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        dismissDialog()
                        callData()
                    }
                }
            }

        }
    }


    private fun postsObserver(state: ResponseState<PostsResponse>) {
        when (state) {
            is ResponseState.Empty,
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
            }

            is ResponseState.NetworkError -> {
                showToast(getString(R.string.network_error))
            }
            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.postsRequestState.value = ResponseState.Empty()
            }
            is ResponseState.Loading -> {
            }
            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        setPostsRV(response.data.postData!!)
                    }
                }
            }

        }
    }

    private fun setStoriesRV() {
        storiesAdapter = StoriesAdapter(
            viewModel.getFakeStories(),
            storyClicked = {
                showToast("Click")
            }
        )
        binding.rvStories.adapter = storiesAdapter
    }

    private fun setPostsRV(list : List<Post>) {
        postsAdapter = PostsAdapter(
            list,
            likeClicked = {
                showToast("Like")
            },
            commentsClicked = {
                showToast("comment")
            },
            sharesClicked = {
                showToast("share")
            }
        )
        binding.rvPosts.adapter = postsAdapter
    }

    private val createPost: (CreatePostModel) -> Unit = { createPostModel ->
        lifecycleScope.launch {
            viewModel.createPost(createPostModel)
        }
    }

    private fun dismissDialog() = try {
        createPostDialog.dismiss()
    } catch (_: Exception) {
    }
}