package com.ieee.codelink.ui.personalInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.databinding.FragmentPersonalInfoBinding
import com.ieee.codelink.ui.main.profile.ProfileViewModel


class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding>(FragmentPersonalInfoBinding::inflate) {
    override val viewModel: ProfileViewModel by viewModels()

}