package com.ieee.codelink.domain.models

import com.ieee.codelink.domain.models.User
import java.io.Serializable

data class Team(
    var id: Int,
    var name: String,
    var description: String,
    var imageUrl: String?,
    var leader_id: Int,
    var members_count: Int,
    private var is_full: Int?,
    var created_at: String,
    var updated_at: String,
    var members: List<User>
):Serializable{

    fun isFull(): Boolean = is_full == 1

}

