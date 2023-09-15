package com.ieee.codelink.data.remote


//Base Url
const val BASE_URL_FOR_IMAGE = "https://0319-197-43-40-254.ngrok-free.app/"
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
const val DELETE_POST = "$GET_POSTS/delete"
const val USER_POSTS = "$GET_POSTS/user/1"
const val GET_LIKED_USERS = "likes"
const val LIKE_A_POST = "like"
const val GET_POST_COMMENTS = "comments"
const val CREATE_COMMENT = "create"
const val SHARE_POST = "share"
//profile
const val PROFILE = "profile"
const val EDIT_INFO = "$PROFILE/edit-info/"
const val EDIT_PROFILE = "$PROFILE/edit-profile/"
//users
const val GET_ALL_USERS = "users"
const val GET_USER = "users/show/"
//teams
const val GET_ALL_TEAMS = "teams"
const val GET_TEAM = "$GET_ALL_TEAMS/show"
const val GET_USER_TEAMS = "$GET_ALL_TEAMS/user-teams"
const val GET_LEADER_TEAMS = "$GET_ALL_TEAMS/leader-teams"
const val CREATE_TEAM = "$GET_ALL_TEAMS/create"
const val EDIT_TEAM = "$GET_ALL_TEAMS/edit/"
const val DELETE_TEAM = "$GET_ALL_TEAMS/delete/"
const val LEAVE_TEAM = "$GET_ALL_TEAMS/leave/"
const val REMOVE_MEMBER_FROM_TEAM = "$GET_ALL_TEAMS/remove-member/"
//invite requests
const val INVITE_REQUESTS = "invite-requests"
const val INVITE_TO_TEAM = "$INVITE_REQUESTS/invite"
const val ACCEPT_TEAM_INVITATION = "$INVITE_REQUESTS/accept-invite"
const val REJECT_TEAM_INVITATION = "$INVITE_REQUESTS/reject-invite"
//join requests
const val JOIN_REQUESTS = "join-requests"
const val JOIN_TEAM = "$JOIN_REQUESTS/join"
const val ACCEPT_JOIN_REQUEST = "$JOIN_REQUESTS/accept-join"
const val REJECT_JOIN_REQUEST = "$JOIN_REQUESTS/reject-join"

const val LEAVE_TEAM_REQUEST = "$BASE_URL/teams/leave"