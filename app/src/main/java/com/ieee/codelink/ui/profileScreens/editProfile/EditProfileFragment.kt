package com.ieee.codelink.ui.profileScreens.editProfile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.data.remote.BASE_URL_FOR_IMAGE
import com.ieee.codelink.databinding.FragmentEditProfileBinding
import com.ieee.codelink.ui.main.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    override val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()
        setOnClicks()
    }

    private fun setViews() {
        lifecycleScope.launch {
            val user = viewModel.getCachedUser()
            binding.apply {
                tvUserName.text = user.name
                tvUserBio.text = user.email

                etBio.setText(user.email)
                etEmail.setText(user.email)
                etName.setText(user.name)

                Glide.with(binding.ivUserImage)
                    .load(BASE_URL_FOR_IMAGE + user.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerInside()
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .into(binding.ivUserImage)
            }
        }
    }

    private fun setOnClicks() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnSave.setOnClickListener {

            }
        }
    }


}