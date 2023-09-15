package com.ieee.codelink.ui.teamsScreens.createTeam

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
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentCreateTeamBinding
import com.ieee.codelink.domain.models.responses.CreateTeamResponse
import com.ieee.codelink.ui.main.search.SearchViewModel
import com.ieee.codelink.ui.teamsScreens.TeamsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateTeamFragment :
    BaseFragment<FragmentCreateTeamBinding>(FragmentCreateTeamBinding::inflate) {
    override val viewModel: TeamsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUri()
        setOnClicks()
        setObserverts()
    }

    private fun initUri() {
        viewModel.createTeamImgUri = null
    }

    private fun setObserverts() {
        viewModel.createTeamState.awareCollect { state ->
            createTeamObserver(state)
        }
    }

    private fun createTeamObserver(state: ResponseState<CreateTeamResponse>) {
        stopLoadingAnimation()
        when (state) {
            is ResponseState.Empty -> {}
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
                viewModel.createTeamState.value = ResponseState.Empty()
            }

            is ResponseState.NetworkError -> {
                showToast(getString(R.string.network_error), false)
                viewModel.createTeamState.value = ResponseState.Empty()
            }

            is ResponseState.Error -> {
                showToast(state.message.toString(), false)
                viewModel.createTeamState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                startLoadingAnimation()
            }

            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        goBack()
                        viewModel.createTeamState.value = ResponseState.Empty()
                    }
                }
            }

        }

    }

    private fun goBack() {
        findNavController().navigateUp()
    }

    private fun stopLoadingAnimation() {
        binding.btnCreate.hideLoading()
    }

    private fun startLoadingAnimation() {
        binding.btnCreate.showLoading()
    }

    private fun setOnClicks() {
        binding.apply {
            ivCamera.setOnClickListener {
               openGallery()
            }
            btnCreate.setOnClickListener {
                val name = etName.text.toString()
                val description = etProject.text.toString()
                if (canCreateTeam(name, description)) {
                    lifecycleScope.launch {
                        viewModel.createTeam(name, description, viewModel.createTeamImgUri)
                    }
                } else {
                    showToast(getString(R.string.completeAll), false)
                }
            }
        }
    }

    private fun canCreateTeam(name: String, description: String): Boolean =
        name.isNullOrEmpty().not() &&
                description.isNullOrEmpty().not()


    private fun openGallery() {
        singlePhotoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    val singlePhotoPicker =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.createTeamImgUri = uri

                    Glide.with(binding.ivTeamImage)
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerInside()
                        .placeholder(R.drawable.ic_profile)
                        .error(R.drawable.ic_profile)
                        .into(binding.ivTeamImage)
                }
            }
        }
}