package com.ieee.codelink.ui.main.chat.chatScreens.inbox

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat.setNestedScrollingEnabled
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.data.fakeDataProvider.FakeProvider
import com.ieee.codelink.databinding.FragmentInboxBinding
import com.ieee.codelink.domain.tempModels.TempChatUser
import com.ieee.codelink.ui.adapters.tempAdapters.InboxAdapter

class FragmentInbox : BaseFragment<FragmentInboxBinding>(FragmentInboxBinding::inflate) {
    override val viewModel : BaseViewModel by viewModels()
    private lateinit var inboxAdapter : InboxAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chats =  FakeProvider.fakeDataProvider.fakeChatsInbox
        setRv(chats)
    }

    private fun setRv(chats: MutableList<TempChatUser>) {
        inboxAdapter = InboxAdapter(
            chats
        ) {
            openChat(it)
        }

        binding.rvInboxChats.adapter = inboxAdapter
        binding.rvInboxChats.isNestedScrollingEnabled = false

    }


    private fun openChat(user : TempChatUser){
        findNavController().navigate(FragmentInboxDirections.actionFragmentInboxToFragmentChat(user))
    }


}