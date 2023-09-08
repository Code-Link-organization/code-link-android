package com.ieee.codelink.domain.models

data class Comment(
    var id: Int? = null,
    var content: String,
    var post_id: Int? = null,
    var user_id: Int? = null,
    var user_name: String? = null,
    var user_imageUrl: String? = null,
    var deleted_at: Any? = null,
    var created_at: String? = null,
    var updated_at: String? = null
)