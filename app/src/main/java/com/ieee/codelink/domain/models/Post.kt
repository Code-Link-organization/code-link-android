package com.ieee.codelink.domain.models

data class Post(
    var id: Int,
    var content: String,
    var image_path: String?,
    var user_id: Int,
    var deleted_at: Any?,
    var created_at: String,
    var updated_at: String,
    var user_name: String,
    var user_imageUrl: String?
)