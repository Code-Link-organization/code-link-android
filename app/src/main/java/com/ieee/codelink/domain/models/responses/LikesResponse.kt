package com.ieee.codelink.domain.models.responses

data class LikesResponse(
    var `data`: Data,
    var errors: Errors,
    var message: String,
    var result: Boolean
)