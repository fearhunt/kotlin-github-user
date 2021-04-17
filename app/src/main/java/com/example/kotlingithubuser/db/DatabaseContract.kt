package com.example.kotlingithubuser.db

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
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()
        }
    }
}