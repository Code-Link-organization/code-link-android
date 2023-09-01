package com.ieee.codelink.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.R
import com.ieee.codelink.common.showDialog
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setOnClicks()
    }

    private fun setViews() {
        binding.apply {
            btnDarkMode.tvSectionTitle.text = getString(R.string.dark_mode)
            btnDarkMode.ivSectionImage.setImageResource(R.drawable.ic_dark_mode)

            btnNotifications.tvSectionTitle.text = getString(R.string.notifications)
            btnNotifications.ivSectionImage.setImageResource(R.drawable.ic_notification)

            btnAboutUs.tvSectionTitle.text = getString(R.string.info)
            btnAboutUs.ivSectionImage.setImageResource(R.drawable.ic_info)

            btnLogout.tvSectionTitle.text = getString(R.string.logout)
            btnLogout.ivSectionImage.setImageResource(R.drawable.ic_logout)


            btnSecurity.tvSectionTitle.text = getString(R.string.privacy_policy)
            btnSecurity.ivSectionImage.setImageResource(R.drawable.ic_privacy_policy)

        }
    }

    private fun setOnClicks() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        interfaceSectionClicks()
        languageSectionClicks()
        supportSectionClicks()
    }


    private fun interfaceSectionClicks() {
        binding.apply {
            btnDarkMode.root.setOnClickListener {
                btnDarkMode.sw.toggle()
            }

            btnNotifications.root.setOnClickListener {
                btnNotifications.sw.toggle()
            }
        }
    }


    private fun languageSectionClicks() {
        //TODO("Not yet implemented")
    }

    private fun supportSectionClicks() {
        binding.btnLogout.root.setOnClickListener {
            logoutClicked()
        }
    }

    private fun logoutClicked() {
        showDialog(requireContext(),
            getString(R.string.logout_from_dialog) ,
            getString(R.string.do_you_want_to_log_out),
            positiveClicked = logOut
        )
    }

    private val logOut:() -> Unit = {
        viewModel.logout()
        goToAuthActivity()
    }

}