package com.ieee.codelink.domain.models

data class TempResponse (
    var result: Boolean ,
    var message: String,
    var `data`: Data,
    var errors:List<Any>
    )