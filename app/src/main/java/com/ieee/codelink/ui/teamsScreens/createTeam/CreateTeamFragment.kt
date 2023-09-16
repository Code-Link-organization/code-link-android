package com.ieee.codelink.ui.teamsScreens.createTeam

import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentCreateTeamBinding
import com.ieee.codelink.domain.models.Team
import com.ieee.codelink.domain.models.responses.CreateTeamResponse
import com.ieee.codelink.ui.teamsScreens.TeamsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateTeamFragment :
    BaseFragment<FragmentCreateTeamBinding>(FragmentCreateTeamBinding::inflate) {

    override val viewModel: TeamsViewModel by viewModels()
    private val args by navArgs<CreateTeamFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val team = args.teamData
        initUri()
        initData(team)
        setOnClicks(team)
        setObserverts()
    }

    private fun initData(team: Team?) {
        team?.let { team ->
            binding.apply {
                etName.setText(team.name)
                etProject.setText(team.description)

                setImageUsingGlide(
                    view = ivTeamImage,
                    image = getImageForGlide(team.imageUrl),
                    errorImage = R.drawable.teamwork
                )

                btnCreate.buttonText = getString(R.string.save)
                btnDeleteAction.isVisible = true
            }
        }
    }

    private fun initUri() {
        viewModel.imgUri = null
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

    private fun setOnClicks(team: Team?) {
        binding.apply {
            ivCamera.setOnClickListener {
                openGallery()
            }
            btnCreate.setOnClickListener {
                if (team == null) {
                    createTeamClicked()
                } else {
                    updateTeamClicked(team)
                }

            }
            btnDeleteAction.setOnClickListener {
                deleteItemClicked(team)
            }
        }
    }

    private fun deleteItemClicked(team: Team?) {
        if (team != null) {
            viewModel.deleteTeam(team)
        }
    }

    private fun updateTeamClicked(team: Team) {
        binding.apply {
            val name = etName.text.toString()
            val description = etProject.text.toString()
            if (viewModel.canUpdateTeam(name, description, team)) {
                lifecycleScope.launch {
                    viewModel.editTeam(name, description, viewModel.imgUri)
                }
            } else {
            }
        }
    }

    private fun createTeamClicked() {
        binding.apply {
            val name = etName.text.toString()
            val description = etProject.text.toString()
            if (canCreateTeam(name, description)) {
                lifecycleScope.launch {
                    viewModel.createTeam(name, description, viewModel.imgUri)
                }
            } else {
                showToast(getString(R.string.completeAll), false)
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
                    viewModel.imgUri = uri

                    Glide.with(binding.ivTeamImage)
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerInside()
                        .placeholder(R.drawable.teamwork)
                        .error(R.drawable.teamwork)
                        .into(binding.ivTeamImage)
                }
            }
        }
}