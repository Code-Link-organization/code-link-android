package com.ieee.codelink.ui.main.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentChatsBinding
import com.ieee.codelink.domain.models.FragmentTab
import com.ieee.codelink.domain.tempModels.TempChatUser
import com.ieee.codelink.ui.adapters.ChatPagerAdapter
import com.ieee.codelink.ui.main.chat.chatScreens.calls.FragmentCalls
import com.ieee.codelink.ui.main.chat.chatScreens.chat.FragmentChat
import com.ieee.codelink.ui.main.chat.chatScreens.friends.FragmentChatFriends
import com.ieee.codelink.ui.main.chat.chatScreens.inbox.FragmentInbox

class ChatsHolderFragment : BaseFragment<FragmentChatsBinding>(FragmentChatsBinding::inflate) {
    override val viewModel: BaseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()

    }

    private fun setupViewPager() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val fragmentList = listOf(
            FragmentTab(
                FragmentInbox(),
                getString(R.string.inbox)
            ),
            FragmentTab(
                FragmentChatFriends(),
                getString(R.string.friends)
            ),
            FragmentTab(
                FragmentCalls(),
                getString(R.string.call)
            ),
        )
        val adapter = ChatPagerAdapter(this, fragmentList)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 1
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "  ${adapter.getPositionTitle(position)}  "
        }.attach()

        viewPager.setCurrentItem(1, false)
    }

     fun openChat(user: TempChatUser) {

        findNavController().navigate(
            ChatsHolderFragmentDirections.actionChatsFragmentToFragmentChat(
                user
            )
        )

    }
}