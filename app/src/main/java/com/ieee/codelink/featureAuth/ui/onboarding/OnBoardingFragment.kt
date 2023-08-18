package com.ieee.codelink.featureAuth.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.ieee.codelink.R
import com.ieee.codelink.common.extension.navigateToAction
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentOnboardingBinding
import com.ieee.codelink.featureAuth.ui.adapters.OnBoardingAdapter
import com.ieee.codelink.featureAuth.ui.auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    override val viewModel: OnBoardingViewModel by viewModels()
    private lateinit var viewPager: ViewPager
    private lateinit var myAdapter: OnBoardingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBoardingAdapter()
        setOnClicks()
    }

    private fun setOnClicks() {
        binding.apply {
            btnNext.setOnClickListener {
                btnNextClicked()
            }

            tvSkip.setOnClickListener {
                navigateToAuth()
            }
        }
    }

    private fun btnNextClicked() {
        val currentItem = viewPager.currentItem
        val nextItem = currentItem + 1

        if (nextItem < myAdapter.count) {
            setViewPagerPosition(nextItem)
        } else {
            navigateToAuth()
        }
    }

    private fun setButtonNextText(currentItem: Int) {
        binding.btnNext.text =
        if (viewModel.isLastPage(currentItem)){
            getString(R.string.LetsStart)
        }else{
            getString(R.string.next)
        }
    }

    private fun setOnBoardingAdapter() {
        viewPager = binding.viewPager
        myAdapter = OnBoardingAdapter(viewModel.onBoardings)
        viewPager.adapter = myAdapter
        binding.dotsIndicator.viewPager = viewPager
        setViewPagerScrollListener(viewPager)
    }

    private fun setViewPagerScrollListener(viewPager: ViewPager) {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(position: Int) {
              setButtonNextText(position)
            }
        })
    }

    private fun navigateToAuth() {
        viewModel.setIsOnBoardingFinished(true)
        findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment())
    }

    private fun setViewPagerPosition(nextItem: Int) {
        viewPager.currentItem = nextItem
    }

}