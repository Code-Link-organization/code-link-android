package com.ieee.codelink.core

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
open class BaseResponse(
    val errors: Errors? = null,
    var message: String? = "",
    val status: Int? = 200,
    val result: String? = "success",
    @SerializedName("last_step")
    val lastStep: String? = null,
) : Parcelable {


    fun printClassData(): String {
        return "{" +
                "   errors: $errors," +
                "   message: $message," +
                "   status: $status," +
                "   result: $result," +
                "   lastStep: $lastStep," +
                "}"
    }
}