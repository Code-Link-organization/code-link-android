package com.ieee.codelink.ui.main.search

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.R
import com.ieee.codelink.common.dp
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentSearchBinding
import com.ieee.codelink.domain.models.TempSearchItem
import com.ieee.codelink.ui.adapters.SearchItemsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override val viewModel: SearchViewModel by viewModels()
    private var myAdapter: SearchItemsAdapter? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        getLastScreen()
    }

    private fun getLastScreen() {
        if (viewModel.x % 2 == 0) {
            setServiceLayout()
        } else {
            setTrackLayout()
        }
    }

    private fun setViews() {
        val userImage = R.drawable.ic_onboarding_1
        binding.searchBar.ivUserImage.setImageResource(userImage)
    }

    private fun setServiceLayout() {
        setUpServiceLayoutDimentions()
        setUpServicesRecyclerView()
    }


    private fun setTrackLayout() {
        setUpTrackLayoutDimentions()
         setUpTrackRecyclerView()
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
     //   rvLayout.setPadding(0,0,0,R.dimen.margin_80)
        binding.rvItems.setPadding(8.dp(),10.dp(),8.dp(),150.dp())

    }

    private fun setUpServicesRecyclerView() = try {
        myAdapter!!.items = viewModel.getServices()
        myAdapter!!.notifyDataSetChanged()
    } catch (e: Exception) {
        val rv = binding.rvItems
        initRecyclerView(rv, viewModel.getServices())
    }


    private fun setUpTrackRecyclerView() = try {
        myAdapter!!.items = viewModel.getTracks()
        myAdapter!!.notifyDataSetChanged()
    } catch (e: Exception) {
        val rv = binding.rvItems
        initRecyclerView(rv, viewModel.getTracks())
    }




    private fun initRecyclerView(rv: RecyclerView, list: ArrayList<TempSearchItem>) {
        myAdapter = SearchItemsAdapter(list) {
            viewModel.x++
            if (viewModel.x % 2 == 0) {
                setServiceLayout()
            } else {
                setTrackLayout()
            }
        }
        rv.adapter = myAdapter
        val gridLayoutManager = GridLayoutManager(
            binding.root.context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        rv.layoutManager = gridLayoutManager
    }

}