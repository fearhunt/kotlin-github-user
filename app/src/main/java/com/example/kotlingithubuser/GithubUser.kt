package com.example.kotlingithubuser

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser (
    var username: String? = null,
    var email: String? = null,
    var avatar_url: String? = null,
    var company: String? = null,
    var location: String? = null,
    var public_repos: Int? = null,
    var following: Int? = null,
    var followers: Int? = null
) : Parcelable
