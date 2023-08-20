package com.ieee.codelink.featureAuth.ui.signup

import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.featureAuth.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class signupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel() {

}

