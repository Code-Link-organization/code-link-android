package com.ieee.codelink.ui.main.chat.chatScreens.calls

import androidx.fragment.app.viewModels
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentCallsBinding
import com.ieee.codelink.databinding.FragmentInboxBinding

class FragmentCalls : BaseFragment<FragmentCallsBinding>(FragmentCallsBinding::inflate) {
    override val viewModel : BaseViewModel by viewModels()

}