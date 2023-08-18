package com.ieee.codelink.featureAuth.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.common.extension.onBackPress
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceManger.isOnboardingFinished = true
        setOnClicks()
    }

    private fun setOnClicks() {
        onBackPress { requireActivity().finish() }

        binding.apply {
            tvSignup.setOnClickListener{
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
            }

//            root.setOnClickListener {
//                navigateToHome("")
//            }

        }
    }

}