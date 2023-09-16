package com.ieee.codelink.ui.profileScreens.editProfile

import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.common.extension.toggle
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.common.setImageUsingGlideCenterCrop
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.remote.BASE_URL_FOR_IMAGE
import com.ieee.codelink.databinding.FragmentEditProfileBinding
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.ui.profileScreens.othersProfile.OthersProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    override val viewModel: OthersProfileViewModel by viewModels()
    lateinit var user: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()
        setOnClicks()
        setObservers()
    }

    private fun setObservers() {
        viewModel.updateProfileState.awareCollect {state ->
            state?.let{
                binding.btnSave.hideLoading()
                when (state) {
                    is ResponseState.Empty->{}
                    is ResponseState.NotAuthorized,
                    is ResponseState.UnKnownError -> {
                        viewModel.updateProfileState.value = ResponseState.Empty()
                    }

                    is ResponseState.NetworkError -> {
                        showToast(getString(R.string.network_error), false)
                        viewModel.updateProfileState.value = ResponseState.Empty()
                    }

                    is ResponseState.Error -> {
                        com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                        viewModel.updateProfileState.value = ResponseState.Empty()
                    }

                    is ResponseState.Loading -> {
                        binding.btnSave.showLoading()
                    }

                    is ResponseState.Success -> {
                        state.data?.let { response ->
                            lifecycleScope.launch {
                                val newUser = response.data.user
                                viewModel.cacheUser(user,newUser)
                                binding.btnSave.hideLoading()
                                setViews()
                                viewModel.updateProfileState.value = ResponseState.Empty()
                            }
                        }
                    }

                }
            }
        }
    }


    private fun setViews() {
        lifecycleScope.launch {
            user = viewModel.getCachedUser()
            binding.apply {
                tvUserName.text = user.name
                tvUserTrack.text = user.track

                etBio.setText(user.bio)
                etTrack.setText(user.track)
                etName.setText(user.name)

                setImageUsingGlideCenterCrop(
                    binding.ivUserImage,
                    getImageForGlide(user.imageUrl)
                )
            }
        }
    }

    private fun setOnClicks() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnSave.setOnClickListener {
             saveClicked()
            }

            ivCamera.setOnClickListener {
                openGallery()
            }
        }
    }

    private fun openGallery() {
        singlePhotoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun saveClicked() {
        binding.apply {
            val name = etName.text.toString()
            val bio = etBio.text.toString()
            val track = etTrack.text.toString()

            val shouldUpdate = viewModel.validateNewData(user, name, bio, track)

            if (shouldUpdate) {
                lifecycleScope.launch {
                    viewModel.editProfile(
                        userId = user.id,
                        name = name,
                        bio = bio,
                        track = track
                    )
                }
            }
        }
    }

    val singlePhotoPicker =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.imageUri = uri
                    Glide.with(binding.ivUserImage)
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .placeholder(R.drawable.ic_profile)
                        .error(R.drawable.ic_profile)
                        .into(binding.ivUserImage)

                }
            }
        }


}