package com.ieee.codelink.domain.models

data class ProfileUser(
    var id: Int,
    var name: String,
    var email: String?,
    var imageUrl: String?,
    var track: String?,
    var bio: String?,
    var governate: String?,
    var university: String?,
    var faculty: String?,
    var birthDate: String?,
    var emailProfile: String?,
    var phoneNumber: String?,
    var projects: String?,
    var progLanguages: String?,
    var cvUrl: String?,
    var githubUrl: String?,
    var linkedinUrl: String?,
    var behanceUrl: String?,
    var facebookUrl: String?,
    var twitterUrl: String?
)