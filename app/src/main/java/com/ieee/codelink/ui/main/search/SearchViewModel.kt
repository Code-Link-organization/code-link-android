package com.ieee.codelink.ui.main.search

import com.bumptech.glide.Glide.init
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.data.fakeDataProvider.FakeDataProvider
import com.ieee.codelink.data.repository.UserRepository
import com.ieee.codelink.domain.tempModels.TempSearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    var firstOption : String? = null

    fun reset() {
        firstOption = null
    }
}