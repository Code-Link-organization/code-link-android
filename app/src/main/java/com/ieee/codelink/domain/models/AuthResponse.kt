package com.ieee.codelink.domain.models

import com.google.gson.annotations.SerializedName
import com.ieee.codelink.core.BaseResponse

data class AuthResponse(
    var success: Boolean,
    var message: String,
    var `data`: Data,
    var errors: Errors
)