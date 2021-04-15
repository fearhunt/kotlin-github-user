package com.example.kotlingithubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser (
    var username: String? = null,
    var email: String? = null,
    var avatarUrl: String? = null,
    var company: String? = null,
    var location: String? = null,
    var publicRepos: Int? = null,
    var following: Int? = null,
    var followers: Int? = null
) : Parcelable
