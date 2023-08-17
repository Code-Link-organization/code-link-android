package com.ieee.codelink.featureAuth.ui.auth.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ieee.codelink.common.extension.navigateToAction
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    override val viewModel: BaseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // TODO just a test code. You can remove it
        binding.btnSkip.setOnClickListener {
//            navigateToAction(
//                action = OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment(),
//                isInclusive = true
//            )

        }


    }

}