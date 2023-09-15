package com.ieee.codelink.ui.main.search.searchScreens.teams

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentSearchTeamsBinding
import com.ieee.codelink.domain.models.Team
import com.ieee.codelink.domain.models.responses.AllTeamsResponse
import com.ieee.codelink.ui.adapters.TeamsAdapter
import com.ieee.codelink.ui.main.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchTeamsFragment : BaseFragment<FragmentSearchTeamsBinding>(FragmentSearchTeamsBinding::inflate) {

    override val viewModel: SearchViewModel by viewModels()
    private val navArgs by navArgs<SearchTeamsFragmentArgs>()
    private lateinit var teamsAdapter: TeamsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        callData()
        setOnClickListeners()
        setObservers()
    }

    private fun setViews() {
        hideAllViews()
        startLoadingAnimation()
    }

    private fun setObservers() {
        viewModel.allTeamsState.awareCollect {state ->
            allTeamsObserver(state)
        }
    }

    private fun allTeamsObserver(state: ResponseState<AllTeamsResponse>) {
        when (state) {
            is ResponseState.Empty->{}
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
                recallData()
                viewModel.allTeamsState.value = ResponseState.Empty()
            }

            is ResponseState.NetworkError -> {
                recallData()
                showToast(getString(R.string.network_error),false)
                viewModel.allTeamsState.value = ResponseState.Empty()
            }

            is ResponseState.Error -> {
                recallData()
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.allTeamsState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                //startLoadingAnimation()
            }

            is ResponseState.Success -> {
                stopLoadingAnimation()
                state.data?.let { response ->
                    lifecycleScope.launch {
                        setRv(response.data.teams)
                        showAllViews()
                        viewModel.allTeamsState.value = ResponseState.Empty()
                    }
                }
            }

        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            llCreateTeam.setOnClickListener{
                openCreateTeamScreen()
            }
        }
    }

    private fun setRv(teams: List<Any>) {
        lifecycleScope.launch(Dispatchers.Main) {
            teamsAdapter = TeamsAdapter(
                teams as MutableList<Team> ,
                joinTeam = {
                    showToast("Join Team")
                },
                openTeam = {
                    openTeam(it)
                }
            )
            binding.rvTeams.adapter = teamsAdapter
        }
    }

    private fun openTeam(team: Team) {
       findNavController().navigate(SearchTeamsFragmentDirections.actionSearchTeamsFragmentToTeamDetailsFragment(team))
    }

    private fun openCreateTeamScreen() {
        findNavController().navigate(SearchTeamsFragmentDirections.actionSearchTeamsFragmentToCreateTeamFragment())

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

    private fun callData() {
        lifecycleScope.launch {
            viewModel.getAllTeams()
        }
    }
    private fun recallData(){
        lifecycleScope.launch {
            delay(1000)
            callData()
        }
    }

    private fun showAllViews(){
//        binding.apply {
//            searchBar.root.isVisible = true
//        }
    }

    private fun hideAllViews(){
//        binding.apply {
//            searchBar.root.isVisible = false
//        }
    }
}