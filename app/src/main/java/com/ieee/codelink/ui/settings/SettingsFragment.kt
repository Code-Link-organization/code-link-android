package com.ieee.codelink.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ieee.codelink.R
import com.ieee.codelink.common.extension.onBackPress
import com.ieee.codelink.common.openBrowser
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.databinding.FragmentSettingsBinding
import com.ieee.codelink.ui.main.MainActivity
import com.zeugmasolutions.localehelper.Locales
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setSelectedLanguage()
        setOnClicks()
        setObservers()
    }

    private fun setObservers() {
        viewModel.deleteAccountState.awareCollect { state ->
            when (state) {
                is ResponseState.Empty -> {}
                is ResponseState.Error,
                is ResponseState.UnKnownError,
                is ResponseState.NetworkError ,
                is ResponseState.NotAuthorized -> {
                    showToast(state.message.toString(),false)
                }
                is ResponseState.Success -> {
                    showToast(state.message.toString())
                    logOut()
                }
                is ResponseState.Loading -> {}
            }
        }
    }

    private fun setViews() {
        setButtonsIcons()
        setInitialButtonsStates()
    }

    private fun setInitialButtonsStates() {
        binding.btnDarkMode.sw.isChecked = viewModel.isDarkMode()
        binding.btnNotifications.sw.isChecked = viewModel.isNotificationsEnabled()
    }

    private fun setButtonsIcons() {
        binding.apply {
            btnDarkMode.tvSectionTitle.text = getString(R.string.dark_mode)
            btnDarkMode.ivSectionImage.setImageResource(R.drawable.ic_dark_mode)

            btnNotifications.tvSectionTitle.text = getString(R.string.notifications)
            btnNotifications.ivSectionImage.setImageResource(R.drawable.ic_notification)

            btnAboutUs.tvSectionTitle.text = getString(R.string.info)
            btnAboutUs.ivSectionImage.setImageResource(R.drawable.ic_info)

            btnContactUs.tvSectionTitle.text = getString(R.string.contact_us)
            btnContactUs.ivSectionImage.setImageResource(R.drawable.ic_contact_us)

            btnDeleteAccount.tvSectionTitle.text = getString(R.string.delete_account)
            btnDeleteAccount.ivSectionImage.setImageResource(R.drawable.ic_remove_user)


            btnSecurity.tvSectionTitle.text = getString(R.string.privacy_policy)
            btnSecurity.ivSectionImage.setImageResource(R.drawable.ic_privacy_policy)

            binding.tvSelectedOption.text = viewModel.getCurrentLanguageAsSystem()

        }
    }

    private fun setOnClicks() {
        onBackPress {
            (requireActivity() as MainActivity).saveSettings()
        }

        binding.btnBack.setOnClickListener {
            (requireActivity() as MainActivity).saveSettings()
        }
        interfaceSectionClicks()
        languageSectionClicks()
        supportSectionClicks()
    }


    private fun interfaceSectionClicks() {
        binding.apply {
            btnDarkMode.root.setOnClickListener {
                btnDarkMode.sw.toggle()
                lifecycleScope.launch {
                    viewModel.toggleDarkMode()
                    val isDarkMode = btnDarkMode.sw.isChecked
                    changeDarkMode(isDarkMode)
                }
            }

            btnNotifications.root.setOnClickListener {
                btnNotifications.sw.toggle()
                viewModel.toggleNotifocationsEnabled()
            }
        }
    }

    private fun changeDarkMode(isDarkMode: Boolean) = if (isDarkMode) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }


    private fun languageSectionClicks() {
        binding.language.setOnClickListener {
            toggleLanguageListVisibility()
            viewModel.setLanguageFlagValue(true)
        }


        binding.spinnerRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (viewModel.dialogLanguageFlag.value) {
                val checkedLanguage = group.findViewById<RadioButton>(checkedId).text.toString()
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.changeLanguage(checkedLanguage)
                    changeAppLanguage(checkedLanguage)
                }
            }
        }

    }


    private fun supportSectionClicks() {
        binding.btnContactUs.root.setOnClickListener {
         showToast("contact us",true)
        }

        binding.btnDeleteAccount.root.setOnClickListener {
        deleteAccount()
        }

        binding.btnSecurity.root.setOnClickListener {
            openBrowser(
                requireContext(),
                "https://gist.github.com/Mohamed02Emad/643d8ea2e5922f33988149bcb6980f9c"
            )
        }

        binding.btnAboutUs.root.setOnClickListener {
            openBrowser(
                requireContext(),
                "https://gist.github.com/Mohamed02Emad/0e3b816e5f0e5e78dbaad1400c786cf5"
            )
        }
    }

    private fun deleteAccount() {
        lifecycleScope.launch {
            viewModel.deleteAccount()
        }
    }


    private fun setSelectedLanguage() {
        viewModel.setLanguageFlagValue(false)
        lifecycleScope.launch(Dispatchers.Main) {
            if (viewModel.getCurrentLanguage() == "Arabic") {
                binding.tvSelectedOption.text = getString(R.string.Arabic)
                binding.optionArabic.isChecked = true
            } else {
                binding.tvSelectedOption.text = getString(R.string.English)
                binding.optionEnglish.isChecked = true
            }
        }
    }

    private fun toggleLanguageListVisibility() {
        viewModel.toggleLanguageRadioVisibility()
        if (viewModel.languageRadioVisible.value) {
            binding.spinnerRadioGroup.visibility = View.VISIBLE
            binding.optionsArrow.setImageResource(R.drawable.ic_upper_arrow)
        } else {
            binding.spinnerRadioGroup.visibility = View.GONE
            binding.optionsArrow.setImageResource(R.drawable.ic_bottom_arrow)
        }
    }

    private fun changeAppLanguage(lang: String) {
        when (lang) {
            getString(R.string.Arabic) -> {
                viewModel.toggleLanguageRadioVisibility()
                binding.tvSelectedOption.text = getString(R.string.Arabic)
                (activity as MainActivity).updateLocale(Locales.Arabic)
            }

            getString(R.string.English) -> {
                viewModel.toggleLanguageRadioVisibility()
                binding.tvSelectedOption.text = getString(R.string.English)
                (activity as MainActivity).updateLocale(Locales.English)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        setViews()
    }


    private fun logOut() {
        viewModel.logout()
        goToAuthActivity()
    }

}
