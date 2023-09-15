package com.ieee.codelink.ui.main.search.searchScreens.individuals

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentSeatchUserBinding
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.domain.models.responses.AllUsersResponse
import com.ieee.codelink.ui.adapters.UsersAdapter
import com.ieee.codelink.ui.main.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchUserFragment : BaseFragment<FragmentSeatchUserBinding>(FragmentSeatchUserBinding::inflate) {
    override val viewModel : SearchViewModel by viewModels()

    private lateinit var usersAdapter : UsersAdapter
    private val navArgs by navArgs<SearchUserFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callData()
        setObservers()
    }

    private fun setObservers() {
        viewModel.allUsersState.awareCollect {state ->
                allUsersObserver(state)
        }
    }

    private fun allUsersObserver(state: ResponseState<AllUsersResponse>) {
        when (state) {
            is ResponseState.Empty->{}
            is ResponseState.NotAuthorized,
            is ResponseState.UnKnownError -> {
                recallData()
                viewModel.allUsersState.value = ResponseState.Empty()
            }

            is ResponseState.NetworkError -> {
                recallData()
              //  showToast(getString(R.string.network_error),false)
                viewModel.allUsersState.value = ResponseState.Empty()
            }

            is ResponseState.Error -> {
                recallData()
                com.ieee.codelink.common.showToast(state.message.toString(), requireContext())
                viewModel.allUsersState.value = ResponseState.Empty()
            }

            is ResponseState.Loading -> {
                startLoadingAnimation()
            }

            is ResponseState.Success -> {
                stopLoadingAnimation()
                state.data?.let { response ->
                    lifecycleScope.launch {
                        setupRv(response.data.users as MutableList<User>)
                        viewModel.allUsersState.value = ResponseState.Empty()
                    }
                }
            }

        }

    }

    private fun setupRv(users: MutableList<User>) {
        users.removeAll{ it.id ==  viewModel.getCachedUserId() }
        lifecycleScope.launch(Dispatchers.Main) {
            usersAdapter = UsersAdapter(
                users,
                openProfile = {
                    openProfile(it.id)
                },
                followAction = {
                    showToast("Follow Action")
                }
            )
            binding.rvUsers.adapter = usersAdapter
        }
    }

    private fun openProfile(id: Int) {
        findNavController().navigate(
            SearchUserFragmentDirections.actionSearchUserFragmentToOthersProfile(
                id
            )
        )
    }

    private fun startLoadingAnimation() {
        if (binding.animationView.isAnimating){
            return
        }
        binding.animationView.apply {
            isGone = false
            playAnimation()
        }
    }

    private fun stopLoadingAnimation() {
        binding.animationView.apply {
            isGone = true
            cancelAnimation()
        }
    }

    private fun callData() {
        lifecycleScope.launch {
            viewModel.getAllUsers()
        }
    }
    private fun recallData(){
        lifecycleScope.launch {
            delay(1000)
            callData()
        }
    }
}