package com.ieee.codelink.domain.models

import com.google.gson.annotations.SerializedName
import com.ieee.codelink.core.BaseResponse

data class AuthResponse (
    @SerializedName("success")
    var success: Boolean,
   // var message: String,
    @SerializedName("data")
    var `data`: Data,
    //var errors: Errors
): BaseResponse()