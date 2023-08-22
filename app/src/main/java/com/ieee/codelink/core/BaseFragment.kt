package com.ieee.codelink.core

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.ieee.codelink.common.event.Event
import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.featureAuth.auth.AuthActivity
import com.ieee.codelink.featureAuth.main.MainActivity
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

abstract class BaseFragment
<VB : ViewBinding>
    (private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> VB) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!
    abstract val viewModel: BaseViewModel

    @Inject
    lateinit var preferenceManger: SharedPreferenceManger

    private var toasty: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflateMethod.invoke(inflater, container, false)
        return binding.root
    }

    fun <T> LiveData<Event<T>>.observeIfNotHandled(result: (T) -> Unit) {
        this.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                result(it)
            }
        }
    }

    fun <T> LiveData<T>.observeIfNotNull(observe: (T) -> Unit) {
        this.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            observe(it)
        }
    }


    fun <T> StateFlow<T>.awareCollect(action: suspend (value: T) -> Unit) {
        lifecycleScope.launchWhenStarted {
            this@awareCollect.collectLatest { action(it) }
        }
    }

    fun <T> SharedFlow<T>.awareCollect(action: suspend (value: T) -> Unit) {
        lifecycleScope.launchWhenStarted {
            this@awareCollect.collectLatest { action(it) }
        }
    }


    fun logout() {
        preferenceManger.bearerToken = ""
        startActivity(Intent(requireActivity(), AuthActivity::class.java))
        requireActivity().finish()
    }

    fun clearHasOpenedTheAppBefore() {
        preferenceManger.isOnboardingFinished = false
    }

    fun showToastIfNotNull(
        message: String?,
        success: Boolean = false,
        hideInRelease: Boolean = false,
    ) {
        if (message == null) return
        showToast(message, success, hideInRelease)
    }

    fun showToast(
        message: String,
        success: Boolean = false,
        hideInRelease: Boolean = false,
    ) {
        if (hideInRelease) return
        toasty?.cancel()
        toasty = if (success) Toasty.success(requireContext(), message, Toasty.LENGTH_SHORT, true)
        else Toasty.error(requireContext(), message, Toasty.LENGTH_SHORT, true)
        toasty?.show()
    }

    private fun saveToken(token: String) {
        preferenceManger.bearerToken = token
    }


    fun navigateToHome(token: String) {
        saveToken(token)
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }


}
