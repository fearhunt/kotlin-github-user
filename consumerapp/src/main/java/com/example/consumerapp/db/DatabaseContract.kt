package com.example.consumerapp.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.example.kotlingithubuser"
    const val SCHEME = "content"

    class GithubUserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "github_user"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val EMAIL = "email"
            const val AVATAR_URL = "avatar_url"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val PUBLIC_REPOS = "public_repos"
            const val FOLLOWING = "following"
            const val FOLLOWERS = "followers"


            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()
        }
    }
}