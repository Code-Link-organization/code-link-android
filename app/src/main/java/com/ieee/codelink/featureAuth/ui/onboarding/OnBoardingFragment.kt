package com.ieee.codelink.featureAuth.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentOnboardingBinding
import com.ieee.codelink.featureAuth.ui.adapters.OnBoardingAdapter
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

    private fun setOnBoardingAdapter() {
        setupAdapter()
        setupViewPager()
        setViewPagerScrollListener()
    }

    private fun setupAdapter() {
        myAdapter = OnBoardingAdapter(viewModel.onBoardings)
    }

    private fun setupViewPager() {
        viewPager = binding.viewPager
        viewPager.adapter = myAdapter
        binding.dotsIndicator.viewPager = viewPager
    }

    private fun setViewPagerScrollListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(position: Int) {
                setButtonNextText(position)
                setSkipButtonVisibility(position)
            }
        })
    }

    private fun setButtonNextText(currentPage: Int) {
        binding.btnNext.text =
            if (viewModel.isLastPage(currentPage)){
                getString(R.string.LetsStart)
            }else{
                getString(R.string.next)
            }
    }

    private fun setSkipButtonVisibility(currentPage: Int) {
        binding.tvSkip.isVisible = viewModel.isLastPage(currentPage).not()
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

    private fun setViewPagerPosition(nextItem: Int) {
        viewPager.currentItem = nextItem
    }

    private fun navigateToAuth() {
        viewModel.setIsOnBoardingFinished(true)
        findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment())
    }

}