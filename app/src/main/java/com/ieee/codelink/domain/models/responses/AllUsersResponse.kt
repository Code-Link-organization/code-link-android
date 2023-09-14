package com.ieee.codelink.domain.models.responses

import com.google.gson.annotations.SerializedName
import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.domain.models.responseData.AllUsersData

data class AllUsersResponse(
    @SerializedName("result")
    var sucess: Boolean,
    @SerializedName("data")
    var `data`: AllUsersData,
): BaseResponse()