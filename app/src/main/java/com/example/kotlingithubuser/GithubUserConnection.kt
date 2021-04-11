package com.example.kotlingithubuser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUserConnection (
    var username: String? = null,
    var repos_url: String? = null,
    var avatar_url: String? = null,
    var html_url: String? = null
) : Parcelable