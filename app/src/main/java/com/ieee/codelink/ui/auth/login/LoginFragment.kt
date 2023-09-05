package com.ieee.codelink.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.R
import com.ieee.codelink.common.extension.onBackPress
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentLoginBinding
import com.ieee.codelink.domain.models.responses.AuthResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceManger.isOnboardingFinished = true
        setOnClicks()
        setObservers()
    }

    private fun setOnClicks() {
        onBackPress { requireActivity().finish() }

        binding.apply {
            tvSignup.setOnClickListener {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
                )
            }

            btnLogin.setOnClickListener {
                lifecycleScope.launch {
                    login()
                }
            }

            tvForgetPassword.setOnClickListener {
                gotoForgetPasswordScreen()
            }
        }
    }

    private fun gotoForgetPasswordScreen() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment(
                true,
                " ",
                " "
            )
        )
    }

    private suspend fun login() {
        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()
        viewModel.login(email, password)
    }

    private fun setObservers() {
        viewModel.loginRequestState.awareCollect { state ->
            loginStateObserver(state)
        }

        viewModel.sendOtpState.awareCollect { state ->
            otpRequestObserver(state)
        }
    }

    private fun otpRequestObserver(state: ResponseState<AuthResponse>) {
        binding.btnLogin.hideLoading()
        when (state) {
            is ResponseState.Success -> {
                goToVerificationScreen()
            }

            is ResponseState.Empty -> {
            }

            is ResponseState.Loading -> {
                binding.btnLogin.showLoading()
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
            LoginFragmentDirections.actionLoginFragmentToVerificationFragment(
                binding.emailEt.text.toString(),
                false
            )
        )
    }

    private fun loginStateObserver(state: ResponseState<AuthResponse>) {
        binding.btnLogin.hideLoading()
        when (state) {
            is ResponseState.Empty,
            is ResponseState.UnKnownError -> {
            }

            is ResponseState.NetworkError -> {
                showToast(getString(R.string.network_error))

            }

            is ResponseState.NotAuthorized -> {
                lifecycleScope.launch {
                    sendOtpToUserEmail(binding.emailEt.text.toString())
                }
            }

            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.loginRequestState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                binding.btnLogin.showLoading()
            }

            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        viewModel.cacheUser(response.data.user)
                        navigateToHome(response.data.user.token!!)
                    }
                }
            }

        }

    }

    private suspend fun sendOtpToUserEmail(email: String) {
        viewModel.sendOtpToUserEmail(email)
    }

}