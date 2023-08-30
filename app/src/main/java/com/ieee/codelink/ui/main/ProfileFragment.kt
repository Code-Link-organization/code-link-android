package com.ieee.codelink.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentLoginBinding
import com.ieee.codelink.databinding.FragmentProfileBinding
import com.ieee.codelink.ui.auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override val viewModel: BaseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    private fun setViews() {
        setSectionsViews()
        setUserViews()
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

            btnReciew.ivSectionImage.setImageResource(R.drawable.ic_chat)
            btnReciew.tvSectionTitle.text = getString(R.string.review)

            btnLogout.ivSectionImage.setImageResource(R.drawable.ic_logout)
            btnLogout.tvSectionTitle.text = getString(R.string.logout)

        }
    }

    private fun setUserViews() {
        //todo user email image and name
        binding.apply {
            ivUserImage.setImageResource(R.drawable.ic_onboarding_3)
            tvUserName.text = "Mohamed Emad"
            tvUserEmail.text = "mohamed_Emad@btats.com"
        }
    }
}