package com.ieee.codelink.ui.auth.forgetPassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ieee.codelink.R
import com.ieee.codelink.common.extension.onBackPress
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentForgetPasswordBinding
import com.ieee.codelink.domain.models.responses.AuthResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgetPasswordFragment :
    BaseFragment<FragmentForgetPasswordBinding>(FragmentForgetPasswordBinding::inflate) {

    override val viewModel: ForgetPasswordViewModel by viewModels()
    private val args by navArgs<ForgetPasswordFragmentArgs>()

    private lateinit var email: String
    private var isEmailSection: Boolean = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = args.email
        isEmailSection = args.isEmailSection
        setSectionViews()
    }

    private fun setSectionViews() {
        if (isEmailSection) {
            showEmailSection()
            setEmailSectionOnClicks()
            setEmailSectionObservers()
        } else {
            showPasswordSection()
            setPasswordSectionOnClicks()
            setPasswordSectionObservers()
        }
    }

    private fun setEmailSectionObservers() {


        viewModel.sendOtpState.awareCollect { state ->
            otpRequestObserver(state)
        }
    }


    private fun setPasswordSectionObservers() {
        viewModel.resetPasswordState.awareCollect { state ->
            resetPasswordObserver(state)
        }
    }


    private fun setEmailSectionOnClicks() {
        onBackPress {
            navigateToLogin()
        }

        binding.btnSend.setOnClickListener {
            lifecycleScope.launch {
                viewModel.sendOtpToUserEmail(binding.emailEt.text.toString())
            }
        }
    }

    private fun setPasswordSectionOnClicks() {

        onBackPress {
            isEmailSection = true
            setSectionViews()
        }

        binding.btnSend.setOnClickListener {
            if (isSamePassword()) {
                lifecycleScope.launch {
                    val newPassword = binding.passwordEt.text.toString()
                    val token = args.token
                    viewModel.resetPassword(token, newPassword)
                }
            }
        }
    }

    private fun isSamePassword(): Boolean =
        binding.passwordEt.text.toString() == binding.confirmPasswordEt.text.toString()

    private fun showEmailSection() {
        binding.apply {
            binding.ivLockState.setImageResource(R.drawable.ic_question_mark)
            sendOtpToEmailGroup.visibility = View.VISIBLE
            changePasswordGroup.visibility = View.GONE
            tvMessage.text = getString(R.string.email_section_text)
        }
    }

    private fun showPasswordSection() {
        binding.apply {
            binding.ivLockState.setImageResource(R.drawable.ic_check)
            sendOtpToEmailGroup.visibility = View.GONE
            changePasswordGroup.visibility = View.VISIBLE
            tvMessage.text = getString(R.string.password_section_text)
        }
    }

    private fun otpRequestObserver(state: ResponseState<AuthResponse>) {
        binding.btnSend.hideLoading()
        when (state) {
            is ResponseState.Success -> {
                goToVerificationScreen()
            }

            is ResponseState.Empty -> {
            }

            is ResponseState.Loading -> {
                binding.btnSend.showLoading()
            }

            is ResponseState.Error -> {
                state.message?.let {
                    showToast(state.message.toString())
                }
            }

            is ResponseState.NetworkError -> {
                showToast(getString(R.string.network_error))
            }

            else -> {
                com.ieee.codelink.common.showToast(
                    getString(R.string.something_went_wrong),
                    requireContext()
                )
            }
        }
    }

    private fun resetPasswordObserver(state: ResponseState<AuthResponse>) {
        binding.btnSend.hideLoading()
        when (state) {
            is ResponseState.Success -> {
                com.ieee.codelink.common.showToast(
                    getString(R.string.password_reset_successfully),
                    requireContext()
                )
                navigateToLogin()
            }

            is ResponseState.Empty -> {
            }

            is ResponseState.Loading -> {
                binding.btnSend.showLoading()
            }

            is ResponseState.Error -> {
                state.message?.let {
                    showToast(state.message.toString())
                }
            }

            is ResponseState.NetworkError -> {
                showToast(getString(R.string.network_error))
            }

            else -> {
                com.ieee.codelink.common.showToast(
                    getString(R.string.something_went_wrong),
                    requireContext()
                )
            }
        }
    }

    private fun goToVerificationScreen() {
        findNavController().navigate(
            ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToVerificationFragment(
                binding.emailEt.text.toString(), true
            )
        )
    }

    private fun navigateToLogin() {
        var currentDestinationId = findNavController().currentDestination?.id
        val targetDestinationId = R.id.loginFragment

        var navigatedUp = false

        while (currentDestinationId != null && currentDestinationId != targetDestinationId) {

            navigatedUp = findNavController().navigateUp()

            if (!navigatedUp) {
                break
            }

            currentDestinationId = findNavController().currentDestination?.id
        }
    }
}