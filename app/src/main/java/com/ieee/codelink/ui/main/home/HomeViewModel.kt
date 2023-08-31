package com.ieee.codelink.ui.main.home

import com.facebook.internal.Utility.generateRandomString
import com.ieee.codelink.R
import com.ieee.codelink.common.createRandomString
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.data.repository.UserRepository
import com.ieee.codelink.domain.models.TempPost
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    fun getFakePosts(): ArrayList<TempPost> {
        val list = ArrayList<TempPost>()
        for (i in 0 .. 20){
            list.add(generateRandomPost())
        }
       return list
    }

    fun generateRandomPost():TempPost = TempPost(
        userName = createRandomString(12),
        date = "yesterday",
        userImage = R.drawable.ic_onboarding_1,
        postImage = R.drawable.ic_onboarding_2,
        description = createRandomString(42),
        500,
        200,
        100
    )

}