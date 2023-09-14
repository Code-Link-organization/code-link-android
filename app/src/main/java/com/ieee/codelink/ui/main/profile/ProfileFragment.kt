package com.ieee.codelink.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.R
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.openZoomableImage
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.common.showDialog
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.data.remote.BASE_URL_FOR_IMAGE
import com.ieee.codelink.databinding.FragmentProfileBinding
import com.ieee.codelink.domain.models.User
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setOnClicks()
        setObservers()
    }


    private fun setViews() {
        setSectionsViews()
    }

    private fun setObservers() {
        viewModel.user.awareCollect {
            it?.let{user ->  
                updateUserViews(user)
            }
        }
    }

    private fun updateUserViews(user: User) {
        binding.apply {
            setImageUsingGlide(
                view =binding.ivUserImage,
                image = getImageForGlide(user.imageUrl)
            )
            tvUserName.text = user.name
            tvUserTrack.text = user.track
        }
    }

    private fun setSectionsViews() {
        binding.apply {

            btnUpdateProfile.ivSectionImage.setImageResource(R.drawable.ic_edit_profile)
            btnUpdateProfile.tvSectionTitle.text = getString(R.string.edit_profile)

            btnSettings.ivSectionImage.setImageResource(R.drawable.ic_settings)
            btnSettings.tvSectionTitle.text = getString(R.string.settings)

            btnMyTeams.ivSectionImage.setImageResource(R.drawable.ic_user_teams)
            btnMyTeams.tvSectionTitle.text = getString(R.string.my_teams)

            btnMyCourses.ivSectionImage.setImageResource(R.drawable.ic_class_mates)
            btnMyCourses.tvSectionTitle.text = getString(R.string.my_courses)

            btnLogout.ivSectionImage.setImageResource(R.drawable.ic_logout)
            btnLogout.tvSectionTitle.text = getString(R.string.logout)

        }
    }
    private fun setOnClicks() {
        binding.apply{

            ivUserImage.setOnClickListener {
                userImageClicked()
            }

            ivShareProfile.setOnClickListener {
                shareProfileClicked()
            }

            btnUpdateProfile.root.setOnClickListener{
                updateProfileClicked()
            }
            btnSettings.root.setOnClickListener{
                settingsClicked()
            }
            btnMyTeams.root.setOnClickListener{
                myTeamsClicked()
            }
            btnMyCourses.root.setOnClickListener{
                btnMyCourcesClicked()
            }
            btnLogout.root.setOnClickListener{
                btnLogoutClicked()
            }

            binding.ivPersonalInfo.setOnClickListener{
                personalInfoClicked()
            }

        }
    }


    private fun userImageClicked() {
        viewModel.user.value?.let {
            it.imageUrl?.let {imgUrl->
                openZoomableImage(
                    imgUrl,
                    requireActivity(),
                    binding.ivUserImage
                )
            }
        }

    }
    private fun settingsClicked() {
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
    }
    private fun updateProfileClicked() {
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
    }
    private fun myTeamsClicked() {
        openMyTeamsScreen()
    }

    private fun openMyTeamsScreen() {
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToMyTeamsFragment())
    }

    private fun shareProfileClicked() {
        //TODO("Share profile Not yet implemented")
    }
    private fun btnMyCourcesClicked() {
        //TODO("My Coursrs not yet implemented")
    }
    private fun btnLogoutClicked() {
        showDialog(requireContext(),
            getString(R.string.logout_from_dialog) ,
            getString(R.string.do_you_want_to_log_out),
            positiveClicked = logOut
        )
    }
    private fun personalInfoClicked() {
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToPersonalInfoFragment())
    }

    private val logOut: () -> Unit = {
        viewModel.logout()
        goToAuthActivity()
    }

}