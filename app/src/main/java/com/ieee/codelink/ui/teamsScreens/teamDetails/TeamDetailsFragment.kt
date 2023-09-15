package com.ieee.codelink.ui.teamsScreens.teamDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ieee.codelink.R
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.openZoomableImage
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentTeamDetailsBinding
import com.ieee.codelink.domain.models.Team
import com.ieee.codelink.ui.adapters.TeamMembersAdapter

class TeamDetailsFragment : BaseFragment<FragmentTeamDetailsBinding>(FragmentTeamDetailsBinding::inflate) {
    override val viewModel : BaseViewModel by viewModels()
    private val navArgs by navArgs<TeamDetailsFragmentArgs>()
    private lateinit var membersAdapter : TeamMembersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val team = navArgs.team
        setViews(team)
        setupRv(team)
        setOnClickListeners(team)
    }

    private fun setOnClickListeners(team: Team) {
        binding.apply {
            btnChat.setOnClickListener {
                findNavController().navigateUp()
            }

            ivTeamImage.setOnClickListener {
                val url = getImageForGlide(team.imageUrl)
                if (url !=null){
                    openZoomableImage(url, requireActivity(), binding.ivTeamImage)
                }else{
                    openZoomableImage(R.drawable.teamwork,requireActivity(), binding.ivTeamImage)
                }
            }


        }



    }

    private fun setupRv(team: Team) {
        membersAdapter = TeamMembersAdapter(
            team.members
        ){
            openProfileScreen(it.id)
        }

        binding.rvTeamMembers.adapter = membersAdapter
    }

    private fun setViews(team: Team) {
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

    private fun openProfileScreen(id:Int){
        findNavController().navigate(TeamDetailsFragmentDirections.actionTeamDetailsFragmentToOthersProfile(id))
    }

}