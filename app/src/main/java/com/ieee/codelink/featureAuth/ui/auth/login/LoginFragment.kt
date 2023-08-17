package com.ieee.codelink.featureAuth.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ieee.codelink.common.extension.onBackPress
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceManger.openedTheAppBefore = true
        onBackPress { requireActivity().finish() }

        binding.apply {

            // TODO just a test code. You can remove it
            root.setOnClickListener {
                navigateToHome("")
            }

        }

    }

}