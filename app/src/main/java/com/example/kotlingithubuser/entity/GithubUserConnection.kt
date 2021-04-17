package com.example.kotlingithubuser.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUserConnection (
    var username: String? = null,
    var reposUrl: String? = null,
    var avatarUrl: String? = null,
    var htmlUrl: String? = null
) : Parcelable