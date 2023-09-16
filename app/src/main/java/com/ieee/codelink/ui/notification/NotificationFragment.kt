package com.ieee.codelink.ui.notification

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentNotificationBinding
import com.ieee.codelink.domain.models.Notification
import com.ieee.codelink.domain.models.Team
import com.ieee.codelink.ui.adapters.NotificationsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::inflate) {
    override val viewModel: NotificationsViewModel by viewModels()
    private lateinit var notificationsAdapter: NotificationsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callNotifications()
        setObservers()
    }

    private fun setObservers() {
        notificationsObserver()
    }

    private fun notificationsObserver() {
        viewModel.notificationsState.awareCollect { state ->
            when (state) {
                is ResponseState.Empty -> {}
                is ResponseState.NotAuthorized,
                is ResponseState.UnKnownError -> {
                    showToast(state.message.toString())
                    viewModel.notificationsState.value = ResponseState.Empty()
                }

                is ResponseState.NetworkError -> {
                    showToast(state.message.toString())
                    viewModel.notificationsState.value = ResponseState.Empty()
                }

                is ResponseState.Error -> {
                    showToast(state.message.toString())
                    viewModel.notificationsState.value = ResponseState.Empty()
                }

                is ResponseState.Loading -> {
                    startLoadingAnimation()
                }

                is ResponseState.Success -> {
                    state.data?.let { response ->
                        lifecycleScope.launch {
                            stopLoadingAnimation()
                            val invitations =
                                response.data.notifications as MutableList<Notification>?
                            if (invitations.isNullOrEmpty()) {
                                showNoNotificationsAnimation()
                            } else {
                                setupRv(invitations)
                            }
                            viewModel.notificationsState.value = ResponseState.Empty()
                        }
                    }
                }

            }
        }
    }

    private fun setupRv(notifications: MutableList<Notification>) {
        notificationsAdapter = NotificationsAdapter(
            notifications,
            openProfile = { notification ->
                if (notification.type == "join") {
                    openProfile(notification.user!!.id)
                } else if (notification.team != null) {
                    openTeam(notification.team!!)
                }
            },
            acceptAction = { notification ->
                if (notification.type == "invite") {
                    acceptInvitationActionClicked(notification.id)
                } else if (notification.type == "join") {
                    acceptRequestActionClicked(notification.id)
                }
            },
            rejectAction = { notification ->

                if (notification.type == "invite") {
                    rejectInvitationActionClicked(notification.id)
                } else if (notification.type == "join") {
                    rejectRequestActionClicked(notification.id)
                }
            }
        )
        binding.rvNotifications.adapter = notificationsAdapter
    }

    private fun openTeam(team: Team) {
        findNavController().navigate(
            NotificationFragmentDirections.actionNotificationFragmentToTeamDetailsFragment(
                team
            )
        )
    }

    private fun openProfile(userId: Int) {
        findNavController().navigate(
            NotificationFragmentDirections.actionNotificationFragmentToOthersProfile(
                userId
            )
        )
    }

    private fun rejectInvitationActionClicked(notificationId: Int) {
        lifecycleScope.launch {
            val removeNotification = viewModel.rejectInvitation(notificationId)
            if (removeNotification) {
                removeNotification(notificationId)
            }
        }
    }

    private fun rejectRequestActionClicked(notificationId: Int) {
        lifecycleScope.launch {
            val removeNotification = viewModel.rejectJoinRequest(notificationId)
            if (removeNotification) {
                removeNotification(notificationId)
            }
        }
    }

    private fun acceptInvitationActionClicked(notificationId: Int) {
        lifecycleScope.launch {
            val removeNotification = viewModel.acceptInvitation(notificationId)
            if (removeNotification) {
                removeNotification(notificationId)
            }
        }
    }

    private fun acceptRequestActionClicked(notificationId: Int) {
        lifecycleScope.launch {
            val removeNotification = viewModel.acceptJoinRequest(notificationId)
            if (removeNotification) {
                removeNotification(notificationId)
            }
        }
    }

    private fun removeNotification(notificationId: Int) {
        notificationsAdapter.removeNotification(notificationId)
        if (notificationsAdapter.isEmpty()) {
            showNoNotificationsAnimation()
        }
    }

    private fun startLoadingAnimation() {
        if (binding.animationView.isAnimating) {
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

    private fun showNoNotificationsAnimation() {
        if (binding.lottieNoNotification.isAnimating){
            return
        }
        binding.lottieNoNotification.apply {
            isGone = false
            playAnimation()
        }
    }

    private fun callNotifications() {
        lifecycleScope.launch {
            viewModel.getUserNotificationss()
        }
    }
}