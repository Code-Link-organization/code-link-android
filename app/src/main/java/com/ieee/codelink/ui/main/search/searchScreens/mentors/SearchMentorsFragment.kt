package com.ieee.codelink.ui.main.search.searchScreens.mentors

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentSearchMentorsBinding
import com.ieee.codelink.domain.tempModels.TempMentor
import com.ieee.codelink.domain.tempModels.toMentor
import com.ieee.codelink.domain.tempModels.toTempUserProfile
import com.ieee.codelink.ui.adapters.tempAdapters.MentorsAdapter
import com.ieee.codelink.ui.main.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMentorsFragment :
    BaseFragment<FragmentSearchMentorsBinding>(FragmentSearchMentorsBinding::inflate) {
    override val viewModel: SearchViewModel by viewModels()
    private val navArgs by navArgs<SearchMentorsFragmentArgs>()
    private lateinit var mentorsAdapter: MentorsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRv()
    }

    private fun setRv() {
        val mentors = viewModel.fakeDataProvider.getFakeMentors()
        mentorsAdapter = MentorsAdapter(
            mentors
        ) {
            showToast("Mentor clicked")
        }
        binding.rvMentors.adapter = mentorsAdapter
    }

}