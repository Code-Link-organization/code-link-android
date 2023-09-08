package com.ieee.codelink.data.remote


//Base Url
const val BASE_URL_FOR_IMAGE = "https://2178-197-43-9-216.ngrok-free.app/"
const val BASE_URL = "${BASE_URL_FOR_IMAGE}api/"
//Auth
const val USER ="user"
const val LOGIN_END_POINT = "$USER/login"
const val LOGOUT_END_POINT = "$USER/logout"
const val SIGNUP_END_POINT = "$USER/signup"
const val SEND_VERIFICATION_CODE = "$USER/send-mail"
const val CHECK_VERIFICATION_CODE = "$USER/check-code"
const val CHECK_IF_EMAIL_EXISTS = "$USER/check-email"
const val RESET_PASSWORD = "$USER/reset-password"
//Posts
const val GET_POSTS = "posts"
const val CREATE_POST = "$GET_POSTS/create"
const val SHOW_POST = "$GET_POSTS/show/2"
const val EDIT_POST = "$GET_POSTS/edit/1"
const val DELETE_POST = "$GET_POSTS/delete/2"
const val USER_POSTS = "$GET_POSTS/user/1"
const val GET_LIKED_USERS = "likes"
const val LIKE_A_POST = "like"
const val GET_POST_COMMENTS = "comments"
const val CREATE_COMMENT = "create"

