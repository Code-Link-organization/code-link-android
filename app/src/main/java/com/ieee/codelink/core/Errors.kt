package com.ieee.codelink.core

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Errors(
    @SerializedName("confirm_password")
    val confirmPassword: List<String>?,
    val email: List<String>?,
    val name: List<String>?,
    val password: List<String>?,
    val phone: List<String>?,
    val social: String?,
    val otp: List<String>?,
    @SerializedName("change_password")
    val changePassword: List<String>?,
    val address: List<String>?,
    @SerializedName("country_code")
    val countryCode: List<String>?,
    val title: List<String>?,
    @SerializedName("coupon_code")
    val couponCode: List<String>?,
    val default: List<String>?,
) : Parcelable

fun Errors.toSimpleErrors(): SimpleErrors {
    return SimpleErrors(
        phone = this.phone?.firstOrNull(),
        name = this.name?.firstOrNull(),
        password = this.password?.firstOrNull(),
        confirmPassword = this.confirmPassword?.firstOrNull(),
        otp = this.otp?.firstOrNull(),
        email = this.email?.firstOrNull(),
        changePassword = this.changePassword?.firstOrNull(),
        address = this.address?.firstOrNull(),
        countryCode = this.countryCode?.firstOrNull(),
        title = this.title?.firstOrNull(),
        couponCode = this.couponCode?.firstOrNull(),
        social = this.social,
        default = this.default?.firstOrNull(),
    )
}

data class SimpleErrors(
    val phone: String? = null,
    val name: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,
    val otp: String? = null,
    val email: String? = null,
    val changePassword: String? = null,
    val address: String? = null,
    val countryCode: String? = null,
    val title: String? = null,
    val couponCode: String? = null,
    val social: String? = null,
    val default: String? = null,
)