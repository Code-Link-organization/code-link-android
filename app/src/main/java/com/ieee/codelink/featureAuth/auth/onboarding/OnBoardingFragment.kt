package com.ieee.codelink.featureAuth.auth.onboarding

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentOnboardingBinding
import com.ieee.codelink.featureAuth.auth.adapters.OnBoardingAdapter
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
                setActionButtonText(position)
                setActionButtonSize(position)
                setSkipButtonVisibility(position)
                setIndicatorVisibility(position)
            }
        })
    }

    private fun setActionButtonText(currentPage: Int) {
        binding.btnAction.text =
            if (viewModel.isLastPage(currentPage)){
                getString(R.string.LetsStart)
            }else{
                getString(R.string.next)
            }
    }

    private fun setActionButtonSize(currentPage: Int) {
        var layoutParams = binding.btnAction.layoutParams as ConstraintLayout.LayoutParams

        layoutParams = setActionButtomMargins(layoutParams)
        setActionButtomPadding()
        layoutParams = setActionButtonWidth(layoutParams , currentPage)

        binding.btnAction.layoutParams = layoutParams
    }
    private fun setActionButtomMargins(layoutParams: ConstraintLayout.LayoutParams): ConstraintLayout.LayoutParams {
        layoutParams.marginStart = resources.getDimensionPixelSize(R.dimen.horizontal_padding)
        layoutParams.marginEnd = resources.getDimensionPixelSize(R.dimen.horizontal_padding)
        layoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.margin_20)
        return layoutParams
    }
    private fun setActionButtomPadding() {
        binding.btnAction.setPadding(
            resources.getDimensionPixelSize(R.dimen.button_padding_horizontal),
            resources.getDimensionPixelSize(R.dimen.button_padding_vertical),
            resources.getDimensionPixelSize(R.dimen.button_padding_horizontal),
            resources.getDimensionPixelSize(R.dimen.button_padding_vertical)
        )
    }
    private fun setActionButtonWidth(layoutParams: ConstraintLayout.LayoutParams, currentPage: Int): ConstraintLayout.LayoutParams {
        if (viewModel.isLastPage(currentPage)) {
            layoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        } else {
            layoutParams.width = ConstraintLayout.LayoutParams.WRAP_CONTENT
        }
        return layoutParams
    }

    private fun setSkipButtonVisibility(currentPage: Int) {
        binding.tvSkip.isVisible = viewModel.isLastPage(currentPage).not()
    }

    private fun setIndicatorVisibility(currentPage: Int){
        binding.dotsIndicator.isVisible = viewModel.isLastPage(currentPage).not()
    }

    private fun setOnClicks() {
        binding.apply {
            btnAction.setOnClickListener {
                btnActionClicked()
            }

            tvSkip.setOnClickListener {
                navigateToAuth()
            }
        }
    }

    private fun btnActionClicked() {
        val currentItem = viewPager.currentItem
        val nextItem = currentItem + 1
        val numberOfPages = myAdapter.count

        if (nextItem < numberOfPages ) {
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