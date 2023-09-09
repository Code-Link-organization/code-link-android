package com.ieee.codelink.domain.tempModels

data class TempTeam(
    var name: String,
    var description: String,
    var image: Int,
    var users: List<TempUserSearch>
)