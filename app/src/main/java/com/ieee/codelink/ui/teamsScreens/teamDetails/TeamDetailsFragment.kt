package com.ieee.codelink.ui.teamsScreens.teamDetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.navigateUp
import com.ieee.codelink.R
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.openZoomableImage
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.common.showDialog
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentTeamDetailsBinding
import com.ieee.codelink.domain.models.Team
import com.ieee.codelink.domain.tempModels.TempChatUser
import com.ieee.codelink.ui.adapters.TeamMembersAdapter
import com.ieee.codelink.ui.teamsScreens.TeamsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeamDetailsFragment :
    BaseFragment<FragmentTeamDetailsBinding>(FragmentTeamDetailsBinding::inflate) {
    override val viewModel: TeamsViewModel by viewModels()
    private val navArgs by navArgs<TeamDetailsFragmentArgs>()
    private lateinit var membersAdapter: TeamMembersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val team = navArgs.team
        setViews(team)
        setupRv(team)
        setOnClickListeners(team)
        setObservers()
    }

    private fun setObservers() {
        joinTeamObserver()
        leaveTeamObserver()
    }

    private fun leaveTeamObserver() {
        viewModel.leaveTeamState.awareCollect { state ->
            when (state) {
                is ResponseState.Empty -> {}
                is ResponseState.NetworkError,
                is ResponseState.NotAuthorized,
                is ResponseState.UnKnownError,
                is ResponseState.Error -> {
                    showToast(state.message.toString(), false)
                    binding.btnTeamAction.hideLoading()
                    viewModel.leaveTeamState.value = ResponseState.Empty()
                }

                is ResponseState.Loading -> {
                    binding.btnTeamAction.showLoading()
                }

                is ResponseState.Success -> {
                    showToast(getString(R.string.left), true)
                    binding.btnTeamAction.hideLoading()
                    binding.btnTeamAction.isGone = true
                    findNavController().navigateUp()
                }
            }
        }

    }

    private fun joinTeamObserver() {
        viewModel.joinTeamState.awareCollect { state ->
            when (state) {
                is ResponseState.Empty -> {}
                is ResponseState.NetworkError,
                is ResponseState.NotAuthorized,
                is ResponseState.UnKnownError,
                is ResponseState.Error -> {
                    showToast(state.message.toString(), false)
                    binding.btnTeamAction.hideLoading()
                    viewModel.joinTeamState.value = ResponseState.Empty()
                }

                is ResponseState.Loading -> {
                    binding.btnTeamAction.showLoading()
                }

                is ResponseState.Success -> {
                    showToast(getString(R.string.request_sent), true)
                    binding.btnTeamAction.hideLoading()
                    binding.btnTeamAction.isGone = true
                    viewModel.joinTeamState.value = ResponseState.Empty()
                }
            }
        }
    }

    private fun setOnClickListeners(team: Team) {
        setActionButtonClicks(team)
        binding.apply {
            btnChat.setOnClickListener {
                openChat()
            }
            ivTeamImage.setOnClickListener {
                val url = getImageForGlide(team.imageUrl)
                if (url != null) {
                    openZoomableImage(url, requireActivity(), binding.ivTeamImage)
                } else {
                    openZoomableImage(R.drawable.teamwork, requireActivity(), binding.ivTeamImage)
                }
            }
        }

    }

    private fun openChat() {
       val user = TempChatUser("Team Chat",5,"afa","asfasf")
        findNavController().navigate(TeamDetailsFragmentDirections.actionTeamDetailsFragmentToFragmentChat(user))
    }

    private fun setActionButtonClicks(team: Team) {
        val isTeamLeader = viewModel.isTeamLeader(team)
        val isTeamMember = viewModel.isTeamMember(team)
        binding.btnTeamAction.setOnClickListener {
            if (isTeamMember.not()) {
                joinTeamClicked(team)
            } else if (isTeamLeader) {
                editTeamClicked(team)
            } else {
                showDialog(requireContext(),
                    getString(R.string.leave_team),
                    getString(R.string.sure_leave_team),
                    positiveClicked = {
                        leaveTeamClicked(team)
                    }
                    )
            }
        }
    }

    private fun leaveTeamClicked(team: Team) {
        viewModel.leaveTeam(team)
    }

    private fun editTeamClicked(team: Team) {
        findNavController().navigate(TeamDetailsFragmentDirections.actionTeamDetailsFragmentToCreateTeamFragment(team))
    }

    private fun joinTeamClicked(team: Team) {
        lifecycleScope.launch {
            viewModel.requestToJoinTeam(team)
        }
    }

    private fun setupRv(team: Team) {
        membersAdapter = TeamMembersAdapter(
            team.members
        ) {
            openProfileScreen(it.id)
        }

        binding.rvTeamMembers.adapter = membersAdapter
    }

    private fun setViews(team: Team) {
        setActionButtonText(team)
        binding.apply {
            tvTeamName.text = team.name
            tvTeamDescription.text = team.description
            setImageUsingGlide(
                ivTeamImage,
                getImageForGlide(team.imageUrl),
                errorImage = R.drawable.teamwork
            )
        }
    }

    private fun setActionButtonText(team: Team) {
        val isTeamLeader = viewModel.isTeamLeader(team)
        val isTeamMember = viewModel.isTeamMember(team)

        val text = if (isTeamMember.not()) {
            getString(R.string.join)
        } else if (isTeamLeader) {
            getString(R.string.edit)
        } else {
            getString(R.string.leave)
        }

        binding.btnTeamAction.buttonText = text
    }

    private fun openProfileScreen(id: Int) {
        findNavController().navigate(
            TeamDetailsFragmentDirections.actionTeamDetailsFragmentToOthersProfile(
                id
            )
        )
    }

}