package com.ieee.codelink.ui.main.chat.chatScreens.chat

import androidx.fragment.app.viewModels
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentChatBinding
import com.ieee.codelink.databinding.FragmentChatsBinding
import com.ieee.codelink.databinding.FragmentInboxBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentChat : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {
    override val viewModel : BaseViewModel by viewModels()

}