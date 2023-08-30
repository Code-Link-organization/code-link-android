package com.ieee.codelink.ui.main.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
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

            btnMyContacts.ivSectionImage.setImageResource(R.drawable.ic_profile)
            btnMyContacts.tvSectionTitle.text = getString(R.string.my_contacts)

            btnShare.ivSectionImage.setImageResource(R.drawable.ic_share)
            btnShare.tvSectionTitle.text = getString(R.string.share_with_friends)

            btnCourseMates.ivSectionImage.setImageResource(R.drawable.ic_class_mates)
            btnCourseMates.tvSectionTitle.text = getString(R.string.course_mates)

            btnInfo.ivSectionImage.setImageResource(R.drawable.ic_info)
            btnInfo.tvSectionTitle.text = getString(R.string.info)

            btnReciew.ivSectionImage.setImageResource(R.drawable.ic_reviews)
            btnReciew.tvSectionTitle.text = getString(R.string.review)

            btnLogout.ivSectionImage.setImageResource(R.drawable.ic_logout)
            btnLogout.tvSectionTitle.text = getString(R.string.logout)

        }
    }
    private fun setOnClicks() {
        binding.apply{
            btnLogout.root.setOnClickListener {
                showDialog(requireContext(),
                    "Log Out ?" ,
                    "Are you sure you want to log out?",
                    positiveClicked = logOut
                    )

            }
        }
    }

    private val logOut:() -> Unit = {
        viewModel.logout()
        goToAuthActivity()
    }

}