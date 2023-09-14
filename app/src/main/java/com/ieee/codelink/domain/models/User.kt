package com.ieee.codelink.domain.models

import java.io.Serializable

data class User(
    var id: Int,
    var name: String,
    var email: String,
    var imageUrl : String?,
    var track : String? = null,
    var token: String? = null
):Serializable