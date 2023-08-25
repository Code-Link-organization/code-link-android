package com.ieee.codelink.core

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
open class BaseResponse(
    @SerializedName("errors")
    val errors: Errors? = null,
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("status")
    val status: Int? = 200,
    @SerializedName("success")
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