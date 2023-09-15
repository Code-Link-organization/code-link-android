package com.ieee.codelink.ui.notification

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentNotificationBinding
import com.ieee.codelink.domain.models.InviteRequest
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
        invitationsObserver()
    }

    private fun invitationsObserver() {
        viewModel.invitesState.awareCollect { state ->
            when (state) {
                is ResponseState.Empty -> {}
                is ResponseState.NotAuthorized,
                is ResponseState.UnKnownError -> {
                    viewModel.invitesState.value = ResponseState.Empty()
                }

                is ResponseState.NetworkError -> {
                    viewModel.invitesState.value = ResponseState.Empty()
                }

                is ResponseState.Error -> {
                    viewModel.invitesState.value = ResponseState.Empty()
                }

                is ResponseState.Loading -> {
                    startLoadingAnimation()
                }

                is ResponseState.Success -> {
                    state.data?.let { response ->
                        lifecycleScope.launch {
                            stopLoadingAnimation()
                            val invitations =
                                response.data.invite_requests as MutableList<InviteRequest>
//                            val notifications = invitationsToNotification(invitations)
                            if (invitations.isEmpty()) {
                                showNoNotificationsAnimation()
                            }else {
                                setupRv(invitations as MutableList<InviteRequest>)
                            }
                            viewModel.invitesState.value = ResponseState.Empty()
                        }
                    }
                }

            }
        }
    }

    private fun setupRv(notifications: MutableList<InviteRequest>) {
        notificationsAdapter = NotificationsAdapter(
            notifications,
            openProfile = { userId ->
                openProfile(userId)
            },
            acceptAction = { notidicationId ->
                acceptActionClicked(notidicationId)
            },
            rejectAction = { notidicationId ->
                rejectActionClicked(notidicationId)
            }
        )
        binding.rvNotifications.adapter = notificationsAdapter
    }

    private fun openProfile(userId: Int) {

    }

    private fun rejectActionClicked(notificationId: Int) {
        lifecycleScope.launch {
            val removeNotification = viewModel.rejectInvitation(notificationId)
            if (removeNotification){
                removeNotification(notificationId)

            }
        }
    }

    private fun acceptActionClicked(notificationId: Int) {
        lifecycleScope.launch {
            val removeNotification = viewModel.acceptInvitation(notificationId)
            if (removeNotification){
                removeNotification(notificationId)
            }
        }
    }

    private fun removeNotification(notificationId: Int) {
        notificationsAdapter.removeNotification(notificationId)
        if (notificationsAdapter.isEmpty()){
            showNoNotificationsAnimation()
        }
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
            viewModel.getUserInvitations()
        }
    }
}