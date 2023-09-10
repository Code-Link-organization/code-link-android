package com.ieee.codelink.ui.othersProfile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentOthersProfileBinding
import com.ieee.codelink.domain.tempModels.TempUserProfile
import com.ieee.codelink.domain.tempModels.TempUserSearch
import com.ieee.codelink.domain.tempModels.toTempUserProfile
import com.ieee.codelink.ui.adapters.tempAdapters.FollowersAdapter
import com.ieee.codelink.ui.adapters.tempAdapters.ProfilePostsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OthersProfileFragment :
    BaseFragment<FragmentOthersProfileBinding>(FragmentOthersProfileBinding::inflate) {

    override val viewModel: BaseViewModel by viewModels()
    private val navArgs by navArgs<OthersProfileFragmentArgs>()
    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var postsAdapter: ProfilePostsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userData = navArgs.user
        setViews(userData)
        setOnClicks(userData)
        setupRecyclerViews(userData)
    }

    private fun setViews(userData: TempUserProfile) {
        binding.upperSection.apply {
            tvUserName.text = userData.name
            tvUserEmail.text = userData.track

            followersCount.text = userData.followersCount.toString()
            followingCount.text = userData.followingCount.toString()
            likesCount.text = userData.likesCount.toString()

            Glide.with(ivUserImage)
                .load(userData.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside()
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(ivUserImage)
        }
    }

    private fun setOnClicks(userData: TempUserProfile) {
        setUpperProfileClicks(userData)
        setSocialMediaClicks(userData)
        setFollowersSectionClicks(userData)
    }

    private fun setUpperProfileClicks(userData: TempUserProfile) {
        binding.upperSection.apply {
            btnFollow.setOnClickListener {
                showToast("Follow")
            }
            ivChat.setOnClickListener {
                showToast("open chat")
            }

            ivShare.setOnClickListener {
                showToast("share profile")
            }

            ivUserImage.setOnClickListener {
                showToast("open user image")
            }
        }
    }

    private fun setSocialMediaClicks(userData: TempUserProfile) {
       binding.socialSection.apply {
           ivFacebook.setOnClickListener {
               showToast("open facebook")
           }
           ivTwitter.setOnClickListener {
               showToast("open twitter")

           }
           ivLinkedin.setOnClickListener {
               showToast("open linkedin")

           }
           ivGithub.setOnClickListener {
               showToast("open github")

           }
           ivBehance.setOnClickListener{
               showToast("open behance")

           }
       }
    }

    private fun setFollowersSectionClicks(userData: TempUserProfile) {
      binding.followersSectiom.tvViewAll.setOnClickListener{
          showToast("view all followers")
      }
    }
    private fun setupRecyclerViews(userData: TempUserProfile) {
        setFollowersRv(userData)
        setPostsRv(userData)
    }

    private fun setFollowersRv(userData: TempUserProfile) {

        followersAdapter = FollowersAdapter(
            userData.followers as MutableList<TempUserSearch>
        ) { user ->
            openUserProfile(user)
        }

        binding.followersSectiom.rvFollowers.adapter = followersAdapter
    }

    private fun setPostsRv(userData: TempUserProfile) {
        postsAdapter = ProfilePostsAdapter(
            userData.posts as MutableList<Int>
        ) { image ->
            showToast("open image")
        }

        binding.postsSection.rvPosts.adapter = postsAdapter

    }


    private fun openUserProfile(user: TempUserSearch) {
      val profileUser = user.toTempUserProfile()
      findNavController().navigate(OthersProfileFragmentDirections.actionOthersProfileSelf(profileUser))
    }

}