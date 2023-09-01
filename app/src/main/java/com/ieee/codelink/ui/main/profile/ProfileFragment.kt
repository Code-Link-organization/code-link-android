package com.ieee.codelink.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.R
import com.ieee.codelink.common.showDialog
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentProfileBinding
import com.ieee.codelink.domain.models.User
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }


    private fun setViews() {
        setSectionsViews()
        setOnClicks()
    }

    private fun setObservers() {
        viewModel.user.awareCollect {
            it?.let{user ->  
                updateUserViews(user)
            }
        }
    }

    private fun updateUserViews(user: User) {
        binding.apply {
            // TODO: change image 
            ivUserImage.setImageResource(R.drawable.ic_onboarding_3)
            tvUserName.text = user.name
            tvUserEmail.text = user.email
        }
    }


    private fun setSectionsViews() {
        binding.apply {

            btnUpdateProfile.ivSectionImage.setImageResource(R.drawable.ic_edit_profile)
            btnUpdateProfile.tvSectionTitle.text = getString(R.string.edit_profile)

            btnShare.ivSectionImage.setImageResource(R.drawable.ic_share)
            btnShare.tvSectionTitle.text = getString(R.string.share_with_friends)

            btnMyTeams.ivSectionImage.setImageResource(R.drawable.ic_user_teams)
            btnMyTeams.tvSectionTitle.text = getString(R.string.my_teams)

            btnMyCourses.ivSectionImage.setImageResource(R.drawable.ic_class_mates)
            btnMyCourses.tvSectionTitle.text = getString(R.string.my_courses)

            btnReview.ivSectionImage.setImageResource(R.drawable.ic_reviews)
            btnReview.tvSectionTitle.text = getString(R.string.review)

        }
    }
    private fun setOnClicks() {
        binding.apply{

            ivUserImage.setOnClickListener {
                userImageClicked()
            }

            ivSettings.setOnClickListener {
                settingsClicked()
            }

            btnUpdateProfile.root.setOnClickListener{
                updateProfileClicked()
            }
            btnShare.root.setOnClickListener{
                shareProfileClicked()
            }
            btnMyTeams.root.setOnClickListener{
                myTeamsClicked()
            }
            btnMyCourses.root.setOnClickListener{
                btnMyCourcesClicked()
            }
            btnReview.root.setOnClickListener{
                btnReviewClicked()
            }

        }
    }


    private fun userImageClicked() {
       // TODO("Not yet implemented")
    }
    private fun settingsClicked() {
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
    }
    private fun updateProfileClicked() {
        //TODO("Not yet implemented")
    }
    private fun myTeamsClicked() {
        //TODO("Not yet implemented")
    }
    private fun shareProfileClicked() {
        //TODO("Not yet implemented")
    }
    private fun btnMyCourcesClicked() {
        //TODO("Not yet implemented")
    }
    private fun btnReviewClicked() {
       // TODO("Not yet implemented")
    }

}