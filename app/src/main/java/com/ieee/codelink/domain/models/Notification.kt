package com.ieee.codelink.domain.models

data class Notification(
    var created_at: String,
    var id: Int,
    var status: String,
    var team: Team?,
    var team_id: Int,
    var type: String,
    var updated_at: String,
    var user: User?,
    var user_id: Int
)