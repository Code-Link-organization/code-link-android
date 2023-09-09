package com.ieee.codelink.domain.models

data class SharePostData(
    var id: Int,
    var post_id: Int,
    var content: String,
    var image_path: String?,
    var user_id: Int,
    var user_name: String,
    var user_image: String?,
    var owneruser_id: Int,
    var owner_name: String,
    var owner_image: String?
)