package com.ieee.codelink.domain.models.responses

import com.google.gson.annotations.SerializedName
import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.domain.models.CommentData
import com.ieee.codelink.domain.models.Errors
import com.ieee.codelink.domain.models.PostData

data class CreatePostResponse(
    @SerializedName("result")
    var sucess: Boolean,
    @SerializedName("data")
    var `data`: PostData,
): BaseResponse()