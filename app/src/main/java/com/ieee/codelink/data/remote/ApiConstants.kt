package com.ieee.codelink.data.remote


//Base Url
const val BASE_URL_FOR_IMAGE = "https://29bc-154-185-46-195.ngrok-free.app/"
const val BASE_URL = "${BASE_URL_FOR_IMAGE}api/"
//Auth
const val LOGIN_END_POINT = "user/login"
const val LOGOUT_END_POINT = "user/logout"
const val SIGNUP_END_POINT = "user/signup"
const val SEND_VERIFICATION_CODE = "user/send-mail"
const val CHECK_VERIFICATION_CODE = "user/check-code"
const val CHECK_IF_EMAIL_EXISTS = "user/check-email"
const val RESET_PASSWORD = "user/reset-password"
//Posts
const val GET_POSTS = "posts"
const val CREATE_POST = "posts/create"
const val SHOW_POST = "posts/show/2"
const val EDIT_POST = "posts/edit/1"
const val DELETE_POST = "posts/delete/2"
const val USER_POSTS = "posts/user/1"

