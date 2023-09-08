package com.ieee.codelink.domain.models.responses

data class LikeData(
    var id: Int,
    var user_id: Int,
    var post_id: Int,
    var created_at: String,
    var updated_at: String,
    var user_name: String,
    var user_imageUrl: String
)