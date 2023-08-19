package com.ieee.codelink.featureAuth.domain.models

data class User(
    var name: String,
    var email: String,
    var updated_at: String,
    var created_at: String,
    var id: Int,
    var token: String
)