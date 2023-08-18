package com.ieee.codelink.featureAuth.domain.models

data class AuthResponse(
    var success: Boolean,
    var message: String,
    var `data`: Data,
    var errors: Errors
)