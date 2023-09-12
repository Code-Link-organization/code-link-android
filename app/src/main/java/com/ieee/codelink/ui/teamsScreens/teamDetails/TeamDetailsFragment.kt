package com.ieee.codelink.ui.teamsScreens.teamDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ieee.codelink.R
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentTeamDetailsBinding
import com.ieee.codelink.domain.tempModels.TempTeam
import com.ieee.codelink.ui.adapters.tempAdapters.TeamMembersAdapter

class TeamDetailsFragment : BaseFragment<FragmentTeamDetailsBinding>(FragmentTeamDetailsBinding::inflate) {
    override val viewModel : BaseViewModel by viewModels()
    private val navArgs by navArgs<TeamDetailsFragmentArgs>()
    private lateinit var membersAdapter : TeamMembersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val team = navArgs.team
        setViews(team)
        setupRv(team)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupRv(team: TempTeam) {
        membersAdapter = TeamMembersAdapter(
            team.users
        ){
            showToast(it.name + " Clicked")
        }

        binding.rvTeamMembers.adapter = membersAdapter
    }

    private fun setViews(team: TempTeam) {
       binding.apply {
           tvTeamName.text = team.name
           tvTeamDescription.text = team.description
           setImageUsingGlide(
               ivTeamImage,
               team.image,
               errorImage = R.drawable.teamwork
           )
       }
    }


}