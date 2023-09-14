package com.ieee.codelink.ui.profileScreens.othersProfile

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ieee.codelink.R
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.openBrowser
import com.ieee.codelink.common.openFacebook
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.fakeDataProvider.FakeProvider
import com.ieee.codelink.databinding.FragmentOthersProfileBinding
import com.ieee.codelink.domain.models.ProfileUser
import com.ieee.codelink.domain.models.responses.ProfileUserResponse
import com.ieee.codelink.domain.tempModels.TempUserProfile
import com.ieee.codelink.domain.tempModels.TempUserSearch
import com.ieee.codelink.domain.tempModels.toTempUserProfile
import com.ieee.codelink.ui.adapters.tempAdapters.FollowersAdapter
import com.ieee.codelink.ui.adapters.tempAdapters.ProfilePostsAdapter
import com.ieee.codelink.ui.dialogs.InviteUserDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class OthersProfileFragment :
    BaseFragment<FragmentOthersProfileBinding>(FragmentOthersProfileBinding::inflate) {

    override val viewModel: OthersProfileViewModel by viewModels()
    private val navArgs by navArgs<OthersProfileFragmentArgs>()
    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var postsAdapter: ProfilePostsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = navArgs.userId
        callUser(userId)
        setObservers()
    }

    private fun callUser(userId: Int) {
        lifecycleScope.launch {
            viewModel.getProfileUser(userId)
        }
    }

    private fun setObservers() {
        viewModel.profileUserState.awareCollect { state ->
            state?.let {
                profileUserObserver(state)
            }
        }
    }

    private fun profileUserObserver(state: ResponseState<ProfileUserResponse>) {
        when (state) {
            is ResponseState.Empty,
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
            }

            is ResponseState.NetworkError -> {
                showToast(getString(R.string.network_error), false)
            }

            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.profileUserState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                //todo : if there is time add loading bars to the app
            }

            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        val profileUser = response.data.user
                        setScreenLogic(profileUser)
                    }
                }
            }

        }
    }

    private fun setScreenLogic(profileUser: ProfileUser) {
        setViews(profileUser)
        setSocialMediaVisibility(profileUser)
        setOnClicks(profileUser)
        setupRecyclerViews(FakeProvider.fakeDataProvider.getFakeUsers(1).first().toTempUserProfile())
    }

    private fun setSocialMediaVisibility(profileUser: ProfileUser) {
        val hideFacebook = profileUser.facebookUrl.isNullOrEmpty() || profileUser.facebookUrl.toString().toCharArray().size <= 6
        val hideTwitter = profileUser.twitterUrl.isNullOrEmpty()|| profileUser.twitterUrl.toString().toCharArray().size <= 6
        val hideLinkedIn = profileUser.linkedinUrl.isNullOrEmpty()|| profileUser.linkedinUrl.toString().toCharArray().size <= 6
        val hideGithub = profileUser.githubUrl.isNullOrEmpty()|| profileUser.githubUrl.toString().toCharArray().size <= 6
        val hideBehance = profileUser.behanceUrl.isNullOrEmpty()|| profileUser.behanceUrl.toString().toCharArray().size <= 6

        val hideSection = hideBehance && hideFacebook && hideTwitter && hideLinkedIn && hideGithub

        if (hideSection) {
            binding.socialSection.root.isGone = true
            return
        }
      binding.socialSection.apply {

       if (hideBehance){
           ivBehance.isGone = true
       }

       if (hideFacebook){
           ivFacebook.isGone = true
       }

       if (hideTwitter){
           ivTwitter.isGone = true
       }

       if (hideLinkedIn){
           ivLinkedin.isGone = true
       }

       if (hideGithub){
           ivGithub.isGone = true
       }

      }
    }

    private fun setViews(userData: ProfileUser) {
        binding.upperSection.apply {
            tvUserName.text = userData.name
            tvUserEmail.text = userData.track ?: getString(R.string.empty)

            followersCount.text = (Random.nextInt(150) + 30).toString()
            followingCount.text = (Random.nextInt(150) + 30).toString()
            likesCount.text = (Random.nextInt(150) + 30).toString()

            setImageUsingGlide(
                view = ivUserImage,
                image = getImageForGlide(userData.imageUrl)
            )

        }

        binding.aboutSection.tvAbout.text = userData.bio ?: getString(R.string.empty)

    }

    private fun setOnClicks(userData: ProfileUser) {
        setUpperProfileClicks(userData)
        setSocialMediaClicks(userData)
        setFollowersSectionClicks(userData)
    }

    private fun setUpperProfileClicks(userData: ProfileUser) {
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

    private fun setSocialMediaClicks(userData: ProfileUser) {

        binding.socialSection.apply {
            ivFacebook.setOnClickListener {
                userData.facebookUrl?.let {
                    openFacebook(
                        requireContext(),
                        userData.behanceUrl!!
                    )
                }
            }
            ivTwitter.setOnClickListener {
                userData.twitterUrl?.let {
                    openBrowser(
                        requireContext(),
                        userData.twitterUrl!!
                    )
                }
            }
           ivLinkedin.setOnClickListener {
               userData.linkedinUrl?.let {
                   openBrowser(
                       requireContext(),
                       userData.linkedinUrl!!
                   )
               }
           }
           ivGithub.setOnClickListener {
               userData.githubUrl?.let {
                   openBrowser(
                       requireContext(),
                       userData.githubUrl!!
                   )
               }
           }
           ivBehance.setOnClickListener {
               userData.behanceUrl?.let {
                   openBrowser(
                       requireContext(),
                       userData.behanceUrl!!
                   )
               }
           }
       }
    }

    private fun setFollowersSectionClicks(userData: ProfileUser) {
        binding.followersSectiom.tvViewAll.setOnClickListener {
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
              profileUser.id
          )
      )
    }

    private fun openInviteUserDialog() {
        val inviteScreen = InviteUserDialogFragment(
            FakeProvider.fakeDataProvider.getFakeTeams(Random.nextInt(5)+2)
        ){
            showToast("invited to ${it.size} teams")
        }
        inviteScreen.show(childFragmentManager , "inviteScreen")
    }

}