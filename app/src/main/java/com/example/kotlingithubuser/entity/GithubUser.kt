package com.example.kotlingithubuser.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class GithubUser (
        @PrimaryKey var id: Int? = 0,
        var username: String? = null,
        var email: String? = null,
        var avatarUrl: String? = null,
        var company: String? = null,
        var location: String? = null,
        var publicRepos: Int? = null,
        var following: Int? = null,
        var followers: Int? = null
) : Parcelable
