package com.ieee.codelink.ui.teamsScreens.myTeams

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentMyTeamsBinding
import com.ieee.codelink.databinding.FragmentSearchTeamsBinding
import com.ieee.codelink.domain.tempModels.TempTeam
import com.ieee.codelink.ui.adapters.tempAdapters.MyTeamsAdapter
import com.ieee.codelink.ui.main.search.SearchViewModel
import com.ieee.codelink.ui.main.search.searchScreens.teams.SearchTeamsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyTeamsFragment :
    BaseFragment<FragmentMyTeamsBinding>(FragmentMyTeamsBinding::inflate) {

    override val viewModel: SearchViewModel by viewModels()
    private lateinit var teamsAdapter: MyTeamsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRv()
        setOnClickListeners()
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

    private fun setRv() {
        lifecycleScope.launch(Dispatchers.Main) {
            teamsAdapter = MyTeamsAdapter(
                viewModel.getFakeDataProvider().fakeTeams
            ) {
                openTeam(it)
            }
            binding.rvTeams.adapter = teamsAdapter
        }
    }

    private fun openTeam(team: TempTeam) {
        findNavController().navigate(
            MyTeamsFragmentDirections.actionMyTeamsFragmentToTeamDetailsFragment(
                team
            )
        )
    }

    private fun openCreateTeamScreen() {
        findNavController().navigate(MyTeamsFragmentDirections.actionMyTeamsFragmentToCreateTeamFragment())

    }

}