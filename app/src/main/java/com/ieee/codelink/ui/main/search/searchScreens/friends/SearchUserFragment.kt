package com.ieee.codelink.ui.main.search.searchScreens.friends

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentSeatchUserBinding
import com.ieee.codelink.domain.tempModels.TempUserSearch
import com.ieee.codelink.ui.adapters.tempAdapters.UsersAdapter
import com.ieee.codelink.ui.main.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserFragment : BaseFragment<FragmentSeatchUserBinding>(FragmentSeatchUserBinding::inflate) {
    override val viewModel : SearchViewModel by viewModels()

    private lateinit var usersAdapter : UsersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
    }

    private fun setupRv() {
        usersAdapter = UsersAdapter(
            viewModel.fakeDataProvider.fakeUsers as MutableList<TempUserSearch>,
            openProfile = {
                showToast("Open profile")
            },
            followAction = {
                showToast("Follow Action")
            }
        )
        binding.rvUsers.adapter = usersAdapter
    }


}