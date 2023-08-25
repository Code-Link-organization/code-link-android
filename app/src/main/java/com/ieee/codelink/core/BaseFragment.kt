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
import com.alexon.Akelni_station.common.network.ResultHelper
import com.alexon.Akelni_station.common.network.globalReduceState
import com.ieee.codelink.R
import com.ieee.codelink.common.event.Event
import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.ui.auth.AuthActivity
import com.ieee.codelink.ui.main.MainActivity
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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


    fun <T> StateFlow<ResponseState<T>>.awareCollectWithReduce(
        showNetworkError: Boolean = true,
        showUnknownError: Boolean = true,
        showUnAuthorizedError: Boolean = true,
        showDefaultError: Boolean = true,
        onAny: ((resultHelper: ResultHelper<T>) -> Unit)? = null,
        onSuccess: ((data: T?) -> Unit)? = null,
        onSuccessSingleTime: ((data: T?) -> Unit)? = null,
        onLoading: (() -> Unit)? = null,
        onUnHandledError: ((message: String?, errors: SimpleErrors?) -> Unit)? = null,
        onHandledOrUnHandledError: ((hasBeenHandled: Boolean, message: String?, errors: SimpleErrors?) -> Unit)? = null,
        onHandledUnCompleteUserData: (() -> Unit)? = null,
        onEmptyState: (() -> Unit)? = null,
    ) {
        lifecycleScope.launchWhenStarted {
            this@awareCollectWithReduce.collectLatest {
                globalReduceState(
                    observedData = it,
                    onAny = onAny,
                    onSuccess = onSuccess,
                    onSuccessSingleTime = onSuccessSingleTime,
                    onLoading = onLoading,
                    onUnHandledError = { message, errors ->
                        showErrorIf(showDefaultError, message)
                        onUnHandledError?.invoke(message, errors)
                    },
                    onHandledOrUnHandledError = onHandledOrUnHandledError,
                    onUnCompleteUserData = onHandledUnCompleteUserData,
                    onNetworkError = {
                        showErrorIf(showNetworkError, getString(R.string.network_error))
                    },
                    onUnAuthorizedError = {
                        showErrorIf(showUnAuthorizedError, getString(R.string.un_authorized))
                        logout()
                    },
                    onEmptyState = onEmptyState,
                    onUnKnownError = {
                        showErrorIf(showUnknownError, getString(R.string.unkown_error))
                    }
                )
            }
        }
    }

    private fun showErrorIf(condition: Boolean, error: String?) {
        if (error == null) return
        if (!condition) return
        lifecycleScope.launch { viewModel.error.emit(error) }
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
