package com.ieee.codelink.ui.main.chat.chatScreens.friends

import androidx.fragment.app.viewModels
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentFriendsBinding
import com.ieee.codelink.databinding.FragmentInboxBinding

class FragmentChatFriends : BaseFragment<FragmentFriendsBinding>(FragmentFriendsBinding::inflate) {
    override val viewModel : BaseViewModel by viewModels()

}