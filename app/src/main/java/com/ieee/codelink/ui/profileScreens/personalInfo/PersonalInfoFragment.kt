package com.ieee.codelink.ui.profileScreens.personalInfo

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentPersonalInfoBinding
import com.ieee.codelink.domain.models.ProfileUser
import com.ieee.codelink.domain.models.responses.ProfileUserResponse
import com.ieee.codelink.ui.profileScreens.othersProfile.OthersProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding>(FragmentPersonalInfoBinding::inflate) {
    override val viewModel: OthersProfileViewModel by viewModels()
    private val args by navArgs<PersonalInfoFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = args.userId
        setViewsVisibility(userId)
        callUserData(userId)
        setObservers()
        setOnClickListeners(userId)
    }

    private fun setOnClickListeners(userId: Int) {
        binding.apply {
            btnSave.setOnClickListener {
                lifecycleScope.launch {
                    val governate = etGovernate.text.toString()
                    val university = etUniversity.text.toString()
                    val faculty = etFaculty.text.toString()
                    val birthDate = etBirthDate.text.toString()
                    val emailProfile = etEmail.text.toString()
                    val phoneNumber = etPhoneNumber.text.toString()
                    val projects = etMyProjects.text.toString()
                    val progLanguages = etProgrammingLanguages.text.toString()
                    val cvUrl = etCvLink.text.toString()
                    val facebookUrl = etFacebook.text.toString()
                    val githubUrl = etGitHub.text.toString()
                    val twitterUrl = etTwitter.text.toString()
                    val behanceUrl = etBehance.text.toString()
                    val linkedinUrl = etLinkedIn.text.toString()
                    viewModel.updateUserInfo(
                        userId = userId,
                        governate = governate,
                        university = university,
                        faculty = faculty,
                        birthDate = birthDate,
                        emailProfile = emailProfile,
                        phoneNumber = phoneNumber,
                        projects = projects,
                        progLanguages = progLanguages,
                        cvUrl = cvUrl,
                        facebookUrl = facebookUrl,
                        githubUrl = githubUrl,
                        twitterUrl = twitterUrl,
                        behanceUrl = behanceUrl,
                        linkedinUrl = linkedinUrl
                    )
                }
            }
        }
}

    private fun setObservers() {
        viewModel.profileUserState.awareCollect { state ->
            state?.let {
                profileUserObserver(state)
            }
        }
        viewModel.updateUserDetailsState.awareCollect { state ->
            state?.let {
                updateUserDetailsObserver(state)
            }
        }
    }

    private fun updateUserDetailsObserver(state: ResponseState<BaseResponse>) {
        when (state) {
            is ResponseState.Empty->{}
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
                binding.btnSave.hideLoading()
            }

            is ResponseState.NetworkError -> {
             // showToast(getString(R.string.network_error), false)
                binding.btnSave.hideLoading()
            }

            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                binding.btnSave.hideLoading()
                viewModel.updateUserDetailsState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                binding.btnSave.showLoading()
            }

            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        binding.btnSave.hideLoading()
                    }
                }
            }

        }
    }

    private fun profileUserObserver(state: ResponseState<ProfileUserResponse>) {
        when (state) {
            is ResponseState.Empty->{}
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
                reCallData()
            }

            is ResponseState.NetworkError -> {
              //  showToast(getString(R.string.network_error), false)
                reCallData()
            }

            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                reCallData()
                viewModel.profileUserState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {

            }

            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        val profileUser = response.data.user
                        stopLoadingAnimation()
                        showData(profileUser)
                    }
                }
            }

        }
    }

    private fun showData(profileUser: ProfileUser) {
        binding.apply {
            etGovernate.setText(profileUser.governate ?: "")
            etUniversity.setText(profileUser.university ?: "")
            etFaculty.setText(profileUser.faculty ?: "")
            etBirthDate.setText(profileUser.birthDate ?: "")
            etEmail.setText(profileUser.emailProfile ?: "")
            etPhoneNumber.setText(profileUser.phoneNumber ?: "")
            etMyProjects.setText(profileUser.projects ?: "")
            etProgrammingLanguages.setText(profileUser.progLanguages ?: "")
            etCvLink.setText(profileUser.cvUrl ?: "")
            etFacebook.setText(profileUser.facebookUrl ?: "")
            etGitHub.setText(profileUser.githubUrl ?: "")
            etTwitter.setText(profileUser.twitterUrl ?: "")
            etBehance.setText(profileUser.behanceUrl ?: "")
            etLinkedIn.setText(profileUser.linkedinUrl ?: "")
            scrollView.isGone = false
        }
    }

    private fun callUserData(userId: Int) {
        lifecycleScope.launch {
            viewModel.getProfileUser(userId)
        }
    }

    private fun setViewsVisibility(userId: Int) {
        startLoadingAnimation()
        val cachedUserId = viewModel.getCachedUser().id
        if (userId != cachedUserId){
          hidePersonalFields()
        }
    }

    private fun hidePersonalFields() {
        binding.apply {
            btnSave.isGone = true
            etBehance.isGone = true
            etFacebook.isGone = true
            etCvLink.isGone = true
            etTwitter.isGone = true
            etLinkedIn.isGone = true
            etGitHub.isGone = true
        }
    }

    private fun startLoadingAnimation() {
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

    private fun reCallData(){
        lifecycleScope.launch {
            delay(1000)
            callUserData(args.userId)
        }
    }

}