package com.ieee.codelink.ui.main.search

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.R
import com.ieee.codelink.common.dp
import com.ieee.codelink.common.extension.onBackPress
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentSearchBinding
import com.ieee.codelink.domain.tempModels.TempSearchItem
import com.ieee.codelink.ui.adapters.SearchItemsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override val viewModel: SearchViewModel by viewModels()
    private var myAdapter: SearchItemsAdapter? = null


    override fun onResume() {
        super.onResume()
        setOnClickListeners()
        setServiceLayout()
    }


    private fun setOnClickListeners() {
        onBackPress {
            if (viewModel.firstOption != null) {
              //  startRvAnimationSlideOut()
                viewModel.firstOption=null
                setServiceLayout()
            } else {
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }


    private fun setServiceLayout() {
        lifecycleScope.launch(Dispatchers.Main) {
            startRvAnimationSlideIn()
            setUpServiceLayoutDimentions()
            setUpServicesRecyclerView()
        }
    }

    private fun setTrackLayout() {
        lifecycleScope.launch(Dispatchers.Main) {
            startRvAnimationSlideIn()
            setUpTrackLayoutDimentions()
            setUpTrackRecyclerView()
        }
    }


    private fun setUpServiceLayoutDimentions() {
        val rvLayout = binding.rvLayout
        val frameSearch = binding.frameSearch

        val constraints = rvLayout.layoutParams as ConstraintLayout.LayoutParams
        constraints.topToBottom = frameSearch.id
        constraints.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        constraints.topMargin = resources.getDimensionPixelSize(R.dimen.negative80_margin)
        constraints.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        constraints.height = ConstraintLayout.LayoutParams.WRAP_CONTENT

        rvLayout.layoutParams = constraints
        binding.rvItems.setPadding(8.dp(),10.dp(),8.dp(),0)
    }

    private fun setUpTrackLayoutDimentions() {
        val rvLayout = binding.rvLayout
        val frameSearch = binding.frameSearch

        val constraints = rvLayout.layoutParams as ConstraintLayout.LayoutParams
        constraints.topToBottom = frameSearch.id
        constraints.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        constraints.topMargin = resources.getDimensionPixelSize(R.dimen.margin_20)
        constraints.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        constraints.height = 0

        rvLayout.layoutParams = constraints
        binding.rvItems.setPadding(8.dp(),10.dp(),8.dp(),150.dp())

    }

    private fun setUpServicesRecyclerView() {
        val rv = binding.rvItems
        initRecyclerView(rv, viewModel.fakeDataProvider.fakeServices as ArrayList<TempSearchItem>)
    }


    private fun setUpTrackRecyclerView(){
        val rv = binding.rvItems
        initRecyclerView(rv, viewModel.fakeDataProvider.fakeTracks as ArrayList<TempSearchItem>)
    }




    private fun initRecyclerView(rv: RecyclerView, list: ArrayList<TempSearchItem>) {
        myAdapter = SearchItemsAdapter(list) {
            if (viewModel.firstOption == null) {
                if (it.title == "Teams"){
                 openSearchTeamsScreen(it)
                }else {
                    viewModel.firstOption = it.title
                    setTrackLayout()
                }
            } else {
                goToSearch(viewModel.firstOption!!, it)
            }
        }
        rv.adapter = myAdapter
    }

    private fun goToSearch(firstOption: String, it: TempSearchItem) {
        when (firstOption) {
            "Courses" -> {
             openCoursesScrean(it)
            }

            "Mentor" -> {
                openSearchMentorsScreen(it)
            }

            "Friends" -> {
                openSearchUsersScreen(it)
            }
        }
    }

    private fun openSearchMentorsScreen(it: TempSearchItem) {
        reset()
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToSearchMentorsFragment(it.title)
        )
    }

    private fun openCoursesScrean(it: TempSearchItem) {
        reset()
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToSearchCoursesFragment(it.title)
        )
    }

    private fun openSearchTeamsScreen(track: TempSearchItem) {
        reset()
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToSearchTeamsFragment(
                track.title
            )
        )
    }
    private fun openSearchUsersScreen(track: TempSearchItem) {
        reset()
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToSearchUserFragment(
                track.title
            )
        )
    }

    private fun reset(){
        lifecycleScope.launch {
            myAdapter = null
            viewModel.firstOption = null
        }
    }

    private fun startRvAnimationSlideIn() {
        val slideInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right)
        binding.rvLayout.startAnimation(slideInAnimation)
    }
    private fun startRvAnimationSlideOut() {
        val slideOutAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_left)
        binding.rvLayout.startAnimation(slideOutAnimation)
    }



}