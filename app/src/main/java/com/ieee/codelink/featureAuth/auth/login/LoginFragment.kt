package com.ieee.codelink.featureAuth.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.common.extension.animateLoadingButton
import com.ieee.codelink.common.extension.onBackPress
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentLoginBinding
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
            tvSignup.setOnClickListener{
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
            }

            btnLogin.setOnClickListener{
                lifecycleScope.launch {
                    login()
                }

            }
        }
    }

    private suspend fun login() {
        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()
        viewModel.login(email, password)
    }

    private fun setObservers() {
        loginStateObserver()
    }

    private fun loginStateObserver() {
        viewModel.loginState.awareCollectWithReduce(
            onAny = {
                binding.btnLogin.animateLoadingButton(it.isLoading)
            },
            onSuccessSingleTime = { state ->
                if (state?.result == "true") {
                    state.data.let { userData ->
                        lifecycleScope.launch {
                            viewModel.cacheUser(userData.user)
                            navigateToHome(userData.user.token)
                        }
                    }
                }
            }, onHandledOrUnHandledError = { _, _, errors ->
                binding.emailField.error = errors?.email
                binding.passwordField.error = errors?.password
            }
        )
    }


}