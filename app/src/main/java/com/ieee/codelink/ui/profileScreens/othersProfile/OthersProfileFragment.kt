package com.ieee.codelink.ui.profileScreens.othersProfile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentOthersProfileBinding
import com.ieee.codelink.domain.tempModels.TempUserProfile
import com.ieee.codelink.domain.tempModels.TempUserSearch
import com.ieee.codelink.domain.tempModels.toTempUserProfile
import com.ieee.codelink.ui.adapters.tempAdapters.FollowersAdapter
import com.ieee.codelink.ui.adapters.tempAdapters.ProfilePostsAdapter
import com.ieee.codelink.ui.dialogs.InviteUserDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

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

            setImageUsingGlide(
                view = ivUserImage,
                image = userData.image
            )

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
            ivInviteUser.setOnClickListener {
                openInviteUserDialog()
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
      findNavController().navigate(
          OthersProfileFragmentDirections.actionOthersProfileSelf(
              profileUser
          )
      )
    }

    private fun openInviteUserDialog() {
        val inviteScreen = InviteUserDialogFragment(
            viewModel.getFakeDataProvider().getFakeTeams(Random.nextInt(5)+2)
        ){
            showToast("invited to ${it.size} teams")
        }
        inviteScreen.show(childFragmentManager , "inviteScreen")
    }

}