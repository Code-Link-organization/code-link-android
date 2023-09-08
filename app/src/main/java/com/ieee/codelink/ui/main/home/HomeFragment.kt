package com.ieee.codelink.ui.main.home

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.common.openZoomableImage
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.remote.BASE_URL_FOR_IMAGE
import com.ieee.codelink.databinding.FragmentHomeBinding
import com.ieee.codelink.domain.models.Comment
import com.ieee.codelink.domain.models.CreatePostModel
import com.ieee.codelink.domain.models.LikeData
import com.ieee.codelink.domain.models.Post
import com.ieee.codelink.domain.models.responses.CommentsResponse
import com.ieee.codelink.domain.models.responses.LikesResponse
import com.ieee.codelink.domain.models.responses.PostsResponse
import com.ieee.codelink.ui.adapters.PostsAdapter
import com.ieee.codelink.ui.dialogs.CommentsDialogFragment
import com.ieee.codelink.ui.dialogs.CreatePostDialogFragment
import com.ieee.codelink.ui.dialogs.LikesDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var createPostDialog: CreatePostDialogFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        loadData()
        setOnClicks()
        setObservers()
    }

    private fun loadData() {
        if (viewModel.isFirstCall()) {
            callData()
        }else{
            viewModel.loadPosts()
        }
    }

    private fun setViews() {
        binding.apply {
            Glide.with(binding.addPostBar.ivUserImage)
                .load(BASE_URL_FOR_IMAGE + viewModel.getUser().imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside()
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(binding.addPostBar.ivUserImage)
        }
    }

    private fun setOnClicks() {
        binding.frameAddPost.setOnClickListener {
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

        viewModel.postLikesRequestState.awareCollect { state ->
            postLikesObserver(state)
        }

        viewModel.postCommentsRequestState.awareCollect { state ->
            postCommentsObserver(state)
        }

    }

    private fun postCommentsObserver(state: ResponseState<CommentsResponse>) {
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
                viewModel.postLikesRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                //todo : if there is time add loading bars to the app
            }

            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        openCommentsScreen(response.data.comments , postId = viewModel.openedPostId)
                    }
                }
            }

        }
    }

    private fun postLikesObserver(state: ResponseState<LikesResponse>) {
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
                viewModel.postLikesRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                //todo : if there is time add loading bars to the app
            }

            is ResponseState.Success -> {
                state.data?.let {response ->
                    lifecycleScope.launch {
                       openLikesScreen(response.data.likeData)
                    }
                }
            }

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
                //todo : if there is time add loading bars to the app

            }

            is ResponseState.Success -> {
                state.data?.let {
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
                        setPostsRV(response.data.postData)
                    }
                }
            }

        }


    }

    private fun setPostsRV(list : List<Post>) {
        postsAdapter = PostsAdapter(
            list as MutableList<Post>,

            likeClicked = {
                likePost(it)
            },
            commentsClicked = {
                lifecycleScope.launch {
                    viewModel.getPostComments(it)
                }
            },
            sharesClicked = {
                showToast("share")
            },
            blockClicked = {
                showToast("block")
            },
            saveClicked = {
                showToast("save")
            },
            deleteClicked = {
                showToast("delete")
            },
            openPostImage = { imgUrl, iv ->
                imgUrl?.let {

                    openImageView(
                        imgUrl,
                        iv,
                        requireActivity()
                    )
                }
            },
            showComments = {
                lifecycleScope.launch {
                    viewModel.getPostComments(it)
                }
            },
            showLikes = {
                lifecycleScope.launch {
                    viewModel.getPostLikes(it)
                }

            }
        )
        binding.rvPosts.adapter = postsAdapter
    }

    private fun likePost(post: Post) {
        lifecycleScope.launch {
            val isLiked = viewModel.likePost(post)
            postsAdapter.like(post , isLiked)
        }
    }

    private val createPost: (CreatePostModel) -> Unit = { createPostModel ->
        lifecycleScope.launch {
            viewModel.createPost(createPostModel)
            callData()
            postsAdapter.notifyDataSetChanged()

        }
    }

    private fun dismissDialog() = try {
        createPostDialog.dismiss()
    } catch (_: Exception) {
    }

    private val openImageView : (String, ImageView, Activity) -> Unit = {url,iv,activity ->
        openZoomableImage(
            url,
            activity,
            iv
        )
    }

    private fun openLikesScreen(likeData: List<LikeData>) {
        val likesScreen = LikesDialogFragment(
            likeData as MutableList<LikeData>,
            openProfile = {
                showToast("Open Profile")
            },
            followAction = {
                showToast("Follow")
            }
        )
        likesScreen.show(childFragmentManager, "likesScreen")
    }

    private fun openCommentsScreen(comments: List<Comment>,postId: Int?) {
        postId?.let {
            val commentsScreen = CommentsDialogFragment(
                comments = comments as MutableList<Comment>,
                postId = postId,
                addComment = { postId, content ->
                    lifecycleScope.launch {
                        viewModel.addComment(postId, content)
                    }
                }
            )
            commentsScreen.show(childFragmentManager, "commentsScreen")
        }
    }


}