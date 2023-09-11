package com.ieee.codelink.ui.main.search.searchScreens.cources

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.data.fakeDataProvider.FakeDataProvider
import com.ieee.codelink.databinding.FragmentSearchCoursesBinding
import com.ieee.codelink.ui.adapters.tempAdapters.CoursesItemsAdapter
import com.ieee.codelink.ui.main.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchCoursesFragment :
    BaseFragment<FragmentSearchCoursesBinding>(FragmentSearchCoursesBinding::inflate) {

    override val viewModel: SearchViewModel by viewModels()
    private val navArgs by navArgs<SearchCoursesFragmentArgs>()
    private lateinit var coursesAdapter: CoursesItemsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
    }

    private fun setupRv() {
        lifecycleScope.launch(Dispatchers.Main){
            val list = FakeDataProvider().getFakeCourses(navArgs.track)
            coursesAdapter = CoursesItemsAdapter(
                list
            ) {
                showToast("Open Course")
            }
            binding.rvCourses.adapter = coursesAdapter
        }
    }


}