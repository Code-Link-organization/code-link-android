package com.ieee.codelink.featureAuth.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.common.extension.onBackPress
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentLoginBinding
import com.ieee.codelink.domain.models.AuthResponse
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

//            root.setOnClickListener {
//                navigateToHome("")
//            }

        }
    }

    private suspend fun login() {
        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()
        viewModel.login(email, password)
    }

    private fun setObservers() {
        viewModel.loginRequestState.observe(viewLifecycleOwner) { state ->
            state?.let {
                loginStateObserver(state)
            }
        }
    }

    private fun loginStateObserver(state: ResponseState<AuthResponse>) {
        when (state) {
            is ResponseState.Empty,
            is ResponseState.NetworkError,
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
            }

            is ResponseState.Error -> {
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
            }

            is ResponseState.Loading -> {}
            is ResponseState.Success -> {
                state.data?.let { response ->
                    lifecycleScope.launch {
                        viewModel.cacheUser(response.data.user)
                        navigateToHome(response.data.user.token)
                    }
                }
            }
        }
    }


}