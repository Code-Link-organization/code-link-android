package com.ieee.codelink.domain.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.ieee.codelink.core.BaseResponse

@Keep
data class AuthResponse(
    @SerializedName("result")
    var sucess: Boolean ,
    @SerializedName("data")
    var `data`: Data,
):BaseResponse()