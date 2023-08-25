package com.ieee.codelink.ui.auth.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.facebook.appevents.AppEventsLogger.Companion.getUserData
import com.ieee.codelink.R
import com.ieee.codelink.common.extension.animateLoadingButton
import com.ieee.codelink.common.extension.clickWithThrottle
import com.ieee.codelink.common.extension.navigateToAction
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    override val viewModel: SignupViewModel by viewModels()
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks()
        observations()
    }

    private fun observations() {
        binding.apply {
            viewModel.signUpState.awareCollectWithReduce(
                onAny = {
                    btnSignup.animateLoadingButton(it.isLoading)
                },
                onSuccessSingleTime = {
                    navigateToAction(
                        action =
                        SignUpFragmentDirections.actionSignUpFragmentToVerificationFragment(email)
                    )
                }, onHandledOrUnHandledError = { _, _, errors ->
                    nameField.error = errors?.name
                    emailField.error = errors?.email
                    passwordField.error = errors?.password
                    confirmPasswordField.error = errors?.confirmPassword
                }
            )
        }

    }

    private fun setOnClicks() {
        binding.apply {
            btnSignup.clickWithThrottle {
                getUserData()
                lifecycleScope.launch {
                    viewModel.signup(name, email, password, confirmPassword)
                }
            }
            tvLogin.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun getUserData() {
        binding.apply {
            name = nameEt.text.toString()
            email = emailEt.text.toString()
            password = passwordEt.text.toString()
            confirmPassword = confirmPasswordEt.text.toString()
        }
    }


}