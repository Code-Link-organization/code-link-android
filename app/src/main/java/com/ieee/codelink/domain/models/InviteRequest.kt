package com.ieee.codelink.domain.models

import com.ieee.codelink.domain.models.Team

data class InviteRequest(
    var created_at: String,
    var id: Int,
    var status: String,
    var team: Team?=null,
    var user: User?=null,
    var team_id: Int,
    var type: String,
    var updated_at: String,
    var user_id: Int
)