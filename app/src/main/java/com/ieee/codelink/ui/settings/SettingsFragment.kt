package com.ieee.codelink.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ieee.codelink.R
import com.ieee.codelink.common.extension.onBackPress
import com.ieee.codelink.common.showDialog
import com.ieee.codelink.core.BaseFragment
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

            btnLogout.tvSectionTitle.text = getString(R.string.logout)
            btnLogout.ivSectionImage.setImageResource(R.drawable.ic_logout)


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
        binding.btnLogout.root.setOnClickListener {
            logoutClicked()
        }
        binding.btnSecurity.root.setOnClickListener {

        }

        binding.btnAboutUs.root.setOnClickListener {

        }
    }

    private fun logoutClicked() {
        showDialog(requireContext(),
            getString(R.string.logout_from_dialog) ,
            getString(R.string.do_you_want_to_log_out),
            positiveClicked = logOut
        )
    }

    private val logOut: () -> Unit = {
        viewModel.logout()
        goToAuthActivity()
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
}
