package com.example.kotlingithubuser.helper

import android.database.Cursor
import com.example.kotlingithubuser.db.DatabaseContract
import com.example.kotlingithubuser.entity.GithubUser

object MappingHelper {
    fun mapCursorToArrayList(userFavoriteCursor: Cursor?): ArrayList<GithubUser> {
        val userFavoriteList = ArrayList<GithubUser>()

        userFavoriteCursor?.apply {
            while(moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.GithubUserColumns._ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.GithubUserColumns.USERNAME))
                val avatarUrl = getString(getColumnIndexOrThrow(DatabaseContract.GithubUserColumns.AVATAR_URL))

                userFavoriteList.add(GithubUser(id, username, avatarUrl))
            }
        }

        return userFavoriteList
    }

    fun mapCursorToObject(userFavoriteCursor: Cursor?): GithubUser {
        var githubUser = GithubUser()

        userFavoriteCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.GithubUserColumns._ID))
            val username = getString(getColumnIndexOrThrow(DatabaseContract.GithubUserColumns.USERNAME))
            val avatarUrl = getString(getColumnIndexOrThrow(DatabaseContract.GithubUserColumns.AVATAR_URL))

            githubUser = GithubUser(id, username, avatarUrl)
        }

        return githubUser
    }
}