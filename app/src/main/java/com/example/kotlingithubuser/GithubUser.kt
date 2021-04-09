package com.example.kotlingithubuser

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser (
    var username: String = "",
    var email: String = "",
    var avatar_url: String = "",
    var company: String = "",
    var location: String = "",
    var public_repos: Int = 0,
    var following: Int = 0,
    var followers: Int = 0
) : Parcelable
