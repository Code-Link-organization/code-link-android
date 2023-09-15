package com.ieee.codelink.domain.models.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.domain.models.responseData.TeamData
import com.ieee.codelink.domain.models.responseData.UserData

@Keep
data class TeamResponse(
    @SerializedName("result")
    var sucess: Boolean,
    @SerializedName("data")
    var `data`: TeamData,
):BaseResponse()