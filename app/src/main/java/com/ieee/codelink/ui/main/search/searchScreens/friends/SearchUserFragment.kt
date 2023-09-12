package com.ieee.codelink.ui.main.search.searchScreens.friends

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentSeatchUserBinding
import com.ieee.codelink.domain.tempModels.TempUserSearch
import com.ieee.codelink.domain.tempModels.toTempUserProfile
import com.ieee.codelink.ui.adapters.tempAdapters.UsersAdapter
import com.ieee.codelink.ui.main.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchUserFragment : BaseFragment<FragmentSeatchUserBinding>(FragmentSeatchUserBinding::inflate) {
    override val viewModel : SearchViewModel by viewModels()

    private lateinit var usersAdapter : UsersAdapter
    private val navArgs by navArgs<SearchUserFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
    }

    private fun setupRv() {
        lifecycleScope.launch(Dispatchers.Main) {
            usersAdapter = UsersAdapter(
                viewModel.getFakeDataProvider().fakeUsers,
                navArgs.track + " Developer",
                openProfile = {
                    openProfile(it)
                },
                followAction = {
                    showToast("Follow Action")
                }
            )
            binding.rvUsers.adapter = usersAdapter
        }
    }

    private fun openProfile(it: TempUserSearch) {
        findNavController().navigate(
            SearchUserFragmentDirections.actionSearchUserFragmentToOthersProfile(
                it.toTempUserProfile()
            )
        )
    }


}