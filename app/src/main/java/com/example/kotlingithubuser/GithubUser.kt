package com.example.kotlingithubuser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUser (
    var name: String = "",
    var username: String = "",
    var avatar: String = "",
    var company: String = "",
    var location: String = "",
    var repository: Int = 0,
    var following: Int = 0,
    var follower: Int = 0
) : Parcelable