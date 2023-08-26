package com.ieee.codelink.domain.models

import java.io.Serializable

data class User(
    var id: Int,
    var name: String,
    var email: String,
    var email_verified_at: String?=null,
    var created_at: String,
    var updated_at: String,
    var token: String? = null
):Serializable