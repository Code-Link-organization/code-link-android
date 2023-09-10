package com.ieee.codelink.domain.models

data class Post(
    var id: Int,
    var content: String,
    var image_path: String?,
    var comments_count : Int = 0,
    var likes_count : Int = 0,
    var shares_count : Int = 0,
    var user_id: Int,
    var shareduser_id: Int,
    var deleted_at: String?,
    var created_at: String,
    var updated_at: String,
    var user_name: String,
    var user_imageUrl: String?,
    var isExpanded :Boolean= false
)

