package com.ieee.codelink.domain.tempModels

import java.io.Serializable

data class TempTeam(
    var name: String,
    var description: String,
    var image: Int,
    var users: List<TempUserSearch>
):Serializable