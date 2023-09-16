package com.ieee.codelink.ui.main.chat.chatScreens.friends

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.data.fakeDataProvider.FakeProvider
import com.ieee.codelink.databinding.FragmentFriendsBinding
import com.ieee.codelink.databinding.FragmentInboxBinding
import com.ieee.codelink.domain.tempModels.TempChatUser
import com.ieee.codelink.ui.adapters.tempAdapters.InboxAdapter
import com.ieee.codelink.ui.main.chat.ChatsHolderFragment

class FragmentChatFriends() : BaseFragment<FragmentFriendsBinding>(FragmentFriendsBinding::inflate) {
    override val viewModel : BaseViewModel by viewModels()

    private lateinit var inboxAdapter : InboxAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chats =  FakeProvider.fakeDataProvider.fakeChatsFriends
        setRv(chats)
    }

    private fun setRv(chats: MutableList<TempChatUser>) {
        inboxAdapter = InboxAdapter(
            chats
        ) {
           openChat(it)
        }
        binding.rvFriendsChats.adapter = inboxAdapter
        binding.rvFriendsChats.isNestedScrollingEnabled = false
    }

    private fun openChat(user : TempChatUser){
        (parentFragment as ChatsHolderFragment).openChat(user)
    }


}