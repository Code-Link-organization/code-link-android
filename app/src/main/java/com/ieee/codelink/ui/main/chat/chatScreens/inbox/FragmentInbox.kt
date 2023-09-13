package com.ieee.codelink.ui.main.chat.chatScreens.inbox

import androidx.fragment.app.viewModels
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentInboxBinding

class FragmentInbox : BaseFragment<FragmentInboxBinding>(FragmentInboxBinding::inflate) {
    override val viewModel : BaseViewModel by viewModels()

}