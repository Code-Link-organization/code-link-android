package com.ieee.codelink.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentHomeBinding
import com.ieee.codelink.ui.adapters.PostsAdapter
import com.ieee.codelink.ui.adapters.StoriesAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var storiesAdapter: StoriesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()

    }


    private fun setupRecyclerViews() {
        setStoriesRV()
        setPostsRV()
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

    private fun setPostsRV() {
        postsAdapter = PostsAdapter(
            viewModel.getFakePosts(),
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


}