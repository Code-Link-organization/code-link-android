package com.ieee.codelink.domain

import android.net.Uri

data class CreatePostModel(
    val postPrivacy: String = "Public",
    val content: String? = null,
    val images : List<Uri>? = null
)
