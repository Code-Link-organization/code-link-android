package com.ieee.codelink.ui.main.home

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.R
import com.ieee.codelink.common.extension.onBackPress
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.openZoomableImage
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentHomeBinding
import com.ieee.codelink.domain.models.Comment
import com.ieee.codelink.domain.models.CreatePostModel
import com.ieee.codelink.domain.models.Post
import com.ieee.codelink.domain.models.responseData.LikeData
import com.ieee.codelink.domain.models.responses.CommentsResponse
import com.ieee.codelink.domain.models.responses.CreatePostResponse
import com.ieee.codelink.domain.models.responses.LikesResponse
import com.ieee.codelink.domain.models.responses.PostsResponse
import com.ieee.codelink.domain.models.responses.ShareResponse
import com.ieee.codelink.ui.adapters.PostsAdapter
import com.ieee.codelink.ui.dialogs.CommentsDialogFragment
import com.ieee.codelink.ui.dialogs.CreatePostDialogFragment
import com.ieee.codelink.ui.dialogs.LikesDialogFragment
import com.ieee.codelink.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: MainViewModel by activityViewModels<MainViewModel>()
    private lateinit var postsAdapter: PostsAdapter
    private var createPostDialog: CreatePostDialogFragment? = null
    private var commentsScreen: CommentsDialogFragment? = null
    private var likesScreen: LikesDialogFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setOnClicks()
        setObservers()
        loadData()
    }

    private fun loadData() {
        if (viewModel.isFirstCall()) {
            callData()
        }else{
            stopLoadingAnimation()
            viewModel.loadPosts()
        }
    }

    private fun setViews() {
        binding.apply {
            setImageUsingGlide(
                view = binding.addPostBar.ivUserImage,
                image = getImageForGlide(viewModel.getUser().imageUrl),
            )
        }
    }

    private fun setOnClicks() {

        onBackPress {
            requireActivity().finish()
        }

        binding.addPostBar.btnCreate.setOnClickListener {
           openCreatePostDialog()
        }

        binding.addPostBar.ivUserImage.setOnClickListener {
            openUserProfile(viewModel.getUser().id)
        }

    }

    private fun callData() {
        lifecycleScope.launch {
            viewModel.getHomePosts()
        }
    }

    private fun reCallData() {
        lifecycleScope.launch {
            delay(1000)
            loadData()
        }
    }

    private fun setObservers() {

        viewModel.createPostsRequestState.awareCollect { state ->
            createPostsObserver(state)
        }

        viewModel.postLikesRequestState.awareCollect { state ->
            postLikesObserver(state)
        }

        viewModel.postCommentsRequestState.awareCollect { state ->
            postCommentsObserver(state)
        }

        viewModel.createCommentsRequestState.awareCollect { state ->
            createComment(state)
        }

        viewModel.sharePostRequestState.awareCollect { state ->
            postSharedObserver(state)
        }

        viewModel.postsRequestState.awareCollect { state ->
            postsObserver(state)
        }

        viewModel.deletePostState.awareCollect {pair->
            deletePostObserver(pair)
        }

    }

    private fun deletePostObserver(pair: Pair<ResponseState<BaseResponse>, Int?>) {
        val state = pair.first
        val postId = pair.second
        when (state) {
            is ResponseState.Empty->{}
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
                viewModel.deletePostState.value = Pair(ResponseState.Empty(),null)
            }

            is ResponseState.NetworkError -> {
                showToast(getString(R.string.network_error),false)
                viewModel.deletePostState.value = Pair(ResponseState.Empty(),null)
            }

            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.deletePostState.value = Pair(ResponseState.Empty(),null)
            }

            is ResponseState.Loading -> {
                //todo : if there is time add loading bars to the app
            }

            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        postsAdapter.removePostFromAdapterList(postId)
                        viewModel.deletePostState.value = Pair(ResponseState.Empty(),null)
                    }
                }
            }

        }

    }

    private fun postSharedObserver(state: ResponseState<ShareResponse>) {
        when (state) {
            is ResponseState.Empty,
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
            }

            is ResponseState.NetworkError -> {
                showToast(getString(R.string.network_error),false)
            }

            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.sharePostRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                //todo : if there is time add loading bars to the app
            }

            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        val postId = response.data.post_id
                        postsAdapter.increaseSharesforPost(postId)
                        com.ieee.codelink.common.showToast(getString(R.string.shared),requireContext())
                    }
                }
            }

        }
    }
    private fun createComment(state: ResponseState<CommentsResponse>) {
        when (state) {
            is ResponseState.Empty->{}
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
                viewModel.createCommentsRequestState.value = ResponseState.Empty()
            }

            is ResponseState.NetworkError -> {
                showToast(getString(R.string.network_error),false)
                viewModel.createCommentsRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.createCommentsRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                //todo : if there is time add loading bars to the app
            }

            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        val newComment = response.data.comments.last()
                        postsAdapter.increaseCommentCount(newComment.post_id)
                        commentsScreen?.addCommentToList(newComment)
                        viewModel.createCommentsRequestState.value = ResponseState.Empty()
                    }
                }
            }

        }
    }
    private fun postCommentsObserver(state: ResponseState<CommentsResponse>) {
        when (state) {
            is ResponseState.Empty -> {}
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
                viewModel.postCommentsRequestState.value = ResponseState.Empty()
            }

            is ResponseState.NetworkError -> {
                showToast(getString(R.string.network_error),false)
                viewModel.postCommentsRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.postCommentsRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                startLoadingAnimation()
            }

            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        openCommentsScreen(response.data.comments , postId = viewModel.openedPostId)
                        viewModel.postCommentsRequestState.value = ResponseState.Empty()
                        stopLoadingAnimation()
                    }
                }
            }

        }
    }
    private fun postLikesObserver(state: ResponseState<LikesResponse>) {
        stopLoadingAnimation()
        when (state) {
            is ResponseState.Empty -> {}
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
                viewModel.postLikesRequestState.value = ResponseState.Empty()
            }

            is ResponseState.NetworkError -> {
                showToast(getString(R.string.network_error),false)
                viewModel.postLikesRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.postLikesRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                startLoadingAnimation()
            }

            is ResponseState.Success -> {
                state.data?.let {response ->
                    lifecycleScope.launch {
                       openLikesScreen(response.data.likeData)
                        viewModel.postLikesRequestState.value = ResponseState.Empty()
                    }
                }
            }

        }
    }
    private fun createPostsObserver(state: ResponseState<CreatePostResponse>) {
        when (state) {
            is ResponseState.Empty->{}
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
                viewModel.createPostsRequestState.value = ResponseState.Empty()
            }

            is ResponseState.NetworkError -> {
                showToast(getString(R.string.network_error),false)
                viewModel.createPostsRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.createPostsRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                //todo : if there is time add loading bars to the app
            }

            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        dismissCreatePostDialog()
                        addPostToList(response.data.post)
                        viewModel.createPostsRequestState.value = ResponseState.Empty()
                    }
                }
            }

        }
    }
    private fun postsObserver(state: ResponseState<PostsResponse>) {
        stopLoadingAnimation()
        when (state) {
            is ResponseState.Empty -> {}
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
                reCallData()
                viewModel.postsRequestState.value = ResponseState.Empty()
            }

            is ResponseState.NetworkError -> {
                //showToast(getString(R.string.network_error),false)
                reCallData()
                viewModel.postsRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Error -> {
                reCallData()
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.postsRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                startLoadingAnimation()
            }

            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        if (response.data.postData.isEmpty()){
                            viewModel.postsRequestState.value = ResponseState.Empty()
                            return@launch
                        }
                        setPostsRV(response.data.postData)
                    }
                }
            }

        }
    }
    private fun addPostToList(post: Post) {
        try {
            postsAdapter.addPost(post)
        }catch (_:Exception) {
            callData()
        }
    }
    private fun setPostsRV(list : List<Post>) {
        postsAdapter = PostsAdapter(
            list as MutableList<Post>,
            cachedUserId = viewModel.getUser().id,
            likeClicked = {
                likePost(it)
            },
            commentsClicked = {
                lifecycleScope.launch {
                    viewModel.getPostComments(it)
                }
            },
            sharesClicked = {
               lifecycleScope.launch {
                   viewModel.sharePost(it.id)
               }
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
            },
            openProfile = {
               openUserProfile(it)
            },
            deletePost = {
                lifecycleScope.launch {
                    viewModel.deletePost(it)
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
        }
    }

    private val openImageView : (String, ImageView, Activity) -> Unit = {url,iv,activity ->
        openZoomableImage(
            url,
            activity,
            iv
        )
    }

    private fun openCreatePostDialog() {
        createPostDialog = CreatePostDialogFragment(createPost)
        createPostDialog?.show(childFragmentManager, "create_post")
    }


    private fun openLikesScreen(likeData: List<LikeData>) {
        likesScreen = LikesDialogFragment(
            likeData as MutableList<LikeData>,
            openProfile = {
                openUserProfile(it.user_id)
            },
            followAction = {
                showToast("Follow")
            }
        )
        likesScreen?.show(childFragmentManager, "likesScreen")
    }

    private fun openCommentsScreen(comments: List<Comment>,postId: Int?) {
        postId?.let {
             commentsScreen = CommentsDialogFragment(
                 comments = comments as MutableList<Comment>,
                 postId = postId,
                 addComment = { postId, content ->
                     lifecycleScope.launch {
                         viewModel.addComment(postId, content)
                     }
                 }
             )
            commentsScreen?.show(childFragmentManager, "commentsScreen")
        }
    }

    private fun openUserProfile(userId: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToOthersProfile(userId))
    }


    private fun startLoadingAnimation() {
        if (binding.animationView.isAnimating)return
        binding.animationView.apply {
            isGone = false
            playAnimation()
        }
    }

    private fun stopLoadingAnimation() {
        binding.animationView.apply {
            isGone = true
            cancelAnimation()
        }
    }

    private fun dismissCreatePostDialog() = try {
        createPostDialog?.dismiss()
        createPostDialog = null
    } catch (_: Exception) {
    }

    private fun dismissCommentsDialog() = try {
        commentsScreen?.dismiss()
        commentsScreen = null
    } catch (_: Exception) {
    }

    private fun dismissLikesDialog() = try {
        likesScreen?.dismiss()
        likesScreen = null
    } catch (_: Exception) {
    }

}