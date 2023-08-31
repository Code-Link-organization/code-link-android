package com.ieee.codelink.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentHomeBinding
import com.ieee.codelink.ui.adapters.PostsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var postsAdapter: PostsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

    }


    private fun setupRecyclerView() {
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

        binding.rvHome.adapter = postsAdapter
    }


}