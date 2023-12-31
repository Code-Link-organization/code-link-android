package com.ieee.codelink.ui.teamsScreens.myTeams

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentMyTeamsBinding
import com.ieee.codelink.domain.models.Team
import com.ieee.codelink.domain.models.responses.AllTeamsResponse
import com.ieee.codelink.domain.models.responses.TeamResponse
import com.ieee.codelink.ui.adapters.MyTeamsAdapter
import com.ieee.codelink.ui.teamsScreens.TeamsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyTeamsFragment :
    BaseFragment<FragmentMyTeamsBinding>(FragmentMyTeamsBinding::inflate) {

    override val viewModel: TeamsViewModel by viewModels()
    private lateinit var teamsAdapter: MyTeamsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callUserTeams()
        setOnClickListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.userTeamsState.awareCollect { state ->
            userTeamsObserver(state)
        }

        viewModel.teamState.awareCollect { state ->
            userTeamClickedObserver(state)
        }
    }

    private fun userTeamClickedObserver(state: ResponseState<TeamResponse>) {
        when (state) {
            is ResponseState.Empty->{}
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
                viewModel.teamState.value = ResponseState.Empty()
            }

            is ResponseState.NetworkError -> {
                //  showToast(getString(R.string.network_error),false)
                viewModel.teamState.value = ResponseState.Empty()
            }

            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.teamState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                startLoadingAnimation()
            }

            is ResponseState.Success -> {
                stopLoadingAnimation()
                state.data?.let { response ->
                    lifecycleScope.launch {
                        openTeam(response.data.team)
                        viewModel.teamState.value = ResponseState.Empty()
                    }
                }
            }
        }

    }

    private fun userTeamsObserver(state: ResponseState<AllTeamsResponse>) {
        when (state) {
            is ResponseState.Empty->{}
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
                recallUserTeams()
                viewModel.userTeamsState.value = ResponseState.Empty()
            }

            is ResponseState.NetworkError -> {
                recallUserTeams()
                //  showToast(getString(R.string.network_error),false)
                viewModel.userTeamsState.value = ResponseState.Empty()
            }

            is ResponseState.Error -> {
                recallUserTeams()
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.userTeamsState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                startLoadingAnimation()
            }

            is ResponseState.Success -> {
                stopLoadingAnimation()
                state.data?.let { response ->
                    lifecycleScope.launch {
                        setRv(response.data.teams)
                        viewModel.userTeamsState.value = ResponseState.Empty()
                    }
                }
            }

        }
    }


    private fun setOnClickListeners() {
        binding.apply {
            llCreateTeam.setOnClickListener {
                openCreateTeamScreen()
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setRv(teams: List<Team>) {
        lifecycleScope.launch(Dispatchers.Main) {
            teamsAdapter = MyTeamsAdapter(
                teams as MutableList<Team>
            ) {
                lifecycleScope.launch {
                    viewModel.getTeamById(it.id)
                }
            }
            binding.rvTeams.adapter = teamsAdapter
        }
    }

    private fun openTeam(team: Team) {
        Log.d("mohamed", team.toString())
      findNavController().navigate(MyTeamsFragmentDirections.actionMyTeamsFragmentToTeamDetailsFragment(team))
    }

    private fun openCreateTeamScreen() {
        findNavController().navigate(MyTeamsFragmentDirections.actionMyTeamsFragmentToCreateTeamFragment(null))

    }

    private fun startLoadingAnimation() {
        if (binding.animationView.isAnimating){
            return
        }
        binding.animationView.apply {
            isGone = false
            playAnimation()
        }
    }

    private fun stopLoadingAnimation() {
        binding.animationView.apply {
            isGone = true
            cancelAnimation()
        }
    }

    private fun callUserTeams() {
        startLoadingAnimation()
        lifecycleScope.launch {
            viewModel.getUserTeams()
        }
    }

    private fun recallUserTeams(){
        lifecycleScope.launch {
            delay(1000)
            callUserTeams()
        }
    }

}