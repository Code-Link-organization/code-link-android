package com.ieee.codelink.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

open class BaseViewModel : ViewModel() {

    val error = MutableSharedFlow<String?>()

}