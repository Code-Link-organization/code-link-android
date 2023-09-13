package com.ieee.codelink.ui.main.chat.chatScreens.calls

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.data.fakeDataProvider.FakeProvider
import com.ieee.codelink.databinding.FragmentCallsBinding
import com.ieee.codelink.domain.tempModels.TempChatUser
import com.ieee.codelink.ui.adapters.tempAdapters.CallsAdapter
import com.ieee.codelink.ui.adapters.tempAdapters.InboxAdapter

class FragmentCalls : BaseFragment<FragmentCallsBinding>(FragmentCallsBinding::inflate) {
    override val viewModel : BaseViewModel by viewModels()
    private lateinit var callsAdapter : CallsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chats =  FakeProvider.fakeDataProvider.fakeChatsInbox
        setRv(chats)
    }

    private fun setRv(chats: MutableList<TempChatUser>) {
        callsAdapter = CallsAdapter(
            chats
        ) {
            showToast("Open chat")
        }

        binding.rvInboxChats.adapter = callsAdapter
        binding.rvInboxChats.isNestedScrollingEnabled = false

    }

}