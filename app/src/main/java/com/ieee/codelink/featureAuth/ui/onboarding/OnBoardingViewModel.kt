package com.ieee.codelink.featureAuth.ui.onboarding

import com.ieee.codelink.R
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.featureAuth.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.featureAuth.data.local.preference.SharedPreferenceManger.Companion.IS_ONBOARDING_FINISHED
import com.ieee.codelink.featureAuth.domain.models.OnBoarding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val sharedPreference: SharedPreferenceManger) : BaseViewModel() {

     val onBoardings : ArrayList<OnBoarding> = ArrayList()

    init {
        initOnBoardings()
    }

    private fun initOnBoardings() {
        onBoardings.add(OnBoarding(R.drawable.ic_onboarding_1, "Our platform helps those interested in the field of Software to find what matches their interests.." ))
        onBoardings.add(OnBoarding(R.drawable.ic_onboarding_2, "You can also create or join teams for group projects.." ))
        onBoardings.add(OnBoarding(R.drawable.ic_onboarding_3, "Make the learning process more easier and enjoyable.."))
    }

    fun setIsOnBoardingFinished(isOnBoardingFinished: Boolean) = CoroutineScope(Dispatchers.IO).launch{
        sharedPreference.setValue(IS_ONBOARDING_FINISHED, isOnBoardingFinished)
    }
    fun isFirstPage(currentPage: Int): Boolean = currentPage == 0
    fun isLastPage(currentPage: Int): Boolean = currentPage == onBoardings.size - 1

}