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
class OnBoardingViewModel @Inject constructor(val sharedPreference: SharedPreferenceManger) : BaseViewModel() {


     val onBoardings : ArrayList<OnBoarding> = ArrayList()

    init {
        onBoardings.add(OnBoarding(R.drawable.ic_launcher_background, "Connect with your fellow graduates and build a strong team" ))
        onBoardings.add(OnBoarding(R.drawable.ic_launcher_foreground, "Create or join teams for group projects for graduation project" ))
        onBoardings.add(OnBoarding(R.drawable.ic_launcher_background, "Chat with your team members to discuss ideas"))
    }

    fun setIsOnBoardingFinished(isOnBoardingFinished: Boolean) = CoroutineScope(Dispatchers.IO).launch{
        sharedPreference.setValue(IS_ONBOARDING_FINISHED, isOnBoardingFinished)
    }
    fun isFirstPage(currentPage: Int): Boolean = currentPage == 0
    fun isLastPage(currentPage: Int): Boolean = currentPage == 2

}