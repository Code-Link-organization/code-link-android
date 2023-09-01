package com.ieee.codelink.ui.onboarding

import android.content.Context
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.data.repository.AuthRepository
import com.ieee.codelink.domain.models.OnBoarding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val context: Context,
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val onBoardings: ArrayList<OnBoarding> = ArrayList()

    init {
        initOnBoardings()
    }

    private fun initOnBoardings() {
        onBoardings.add(
            OnBoarding(
                R.drawable.ic_onboarding_1,
                context.getString(R.string.on_boarding_1)
            )
        )
        onBoardings.add(
            OnBoarding(
                R.drawable.ic_onboarding_2,
                context.getString(R.string.on_boarding_2)
            )
        )
        onBoardings.add(
            OnBoarding(
                R.drawable.ic_onboarding_3,
                context.getString(R.string.on_boarding_3)
            )
        )
    }

    fun setIsOnBoardingFinished(isOnBoardingFinished: Boolean) = CoroutineScope(Dispatchers.IO).launch{
        authRepository.setOnBoardingFinished(isOnBoardingFinished)
    }
    fun isFirstPage(currentPage: Int): Boolean = currentPage == 0
    fun isLastPage(currentPage: Int): Boolean = currentPage == onBoardings.size - 1

    fun getCurrentLanguage(): String = authRepository.getCurrentLanguage()

}