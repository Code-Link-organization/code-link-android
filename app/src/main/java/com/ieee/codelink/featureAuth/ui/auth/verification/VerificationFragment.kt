package com.ieee.codelink.featureAuth.ui.auth.verification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ieee.codelink.core.BaseFragment
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.databinding.FragmentVerificationBinding

class VerificationFragment :
    BaseFragment<FragmentVerificationBinding>(FragmentVerificationBinding::inflate) {
    override val viewModel: BaseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks()
    }

    private fun setOnClicks() {
        setKeyboardClicks()
    }

    private fun setKeyboardClicks() {
        binding.keyboard.apply {
            key0.setOnClickListener {addToOtp("0")}
            key1.setOnClickListener {addToOtp("1")}
            key2.setOnClickListener {addToOtp("2")}
            key3.setOnClickListener {addToOtp("3")}
            key4.setOnClickListener {addToOtp("4")}
            key5.setOnClickListener {addToOtp("5")}
            key6.setOnClickListener {addToOtp("6")}
            key7.setOnClickListener {addToOtp("7")}
            key8.setOnClickListener {addToOtp("8")}
            key9.setOnClickListener {addToOtp("9")}
            keyBackspace.setOnClickListener {removeFromOtp()}
        }
    }

    private fun addToOtp(num: String) {
        val otpView = binding.otpView
        val otp =otpView.otp
        otpView.setOTP(otp+num)
    }
    private fun removeFromOtp() {
        val otpView = binding.otpView
        val otp =otpView.otp
        otp?.let {
            otpView.setOTP(otp.dropLast(1))
        }
    }

}