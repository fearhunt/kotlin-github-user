package com.example.consumerapp.helper

import android.database.Cursor
import com.example.consumerapp.db.DatabaseContract.GithubUserColumns
import com.example.consumerapp.entity.GithubUser

object MappingHelper {
    fun mapCursorToArrayList(userFavoriteCursor: Cursor?): ArrayList<GithubUser> {
        val userFavoriteList = ArrayList<GithubUser>()

        userFavoriteCursor?.apply {
            while(moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(GithubUserColumns._ID))
                val username = getString(getColumnIndexOrThrow(GithubUserColumns.USERNAME))
                val email = getString(getColumnIndexOrThrow(GithubUserColumns.EMAIL))
                val avatarUrl = getString(getColumnIndexOrThrow(GithubUserColumns.AVATAR_URL))
                val company = getString(getColumnIndexOrThrow(GithubUserColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(GithubUserColumns.LOCATION))
                val publicRepos = getInt(getColumnIndexOrThrow(GithubUserColumns.PUBLIC_REPOS))
                val following = getInt(getColumnIndexOrThrow(GithubUserColumns.FOLLOWING))
                val followers = getInt(getColumnIndexOrThrow(GithubUserColumns.FOLLOWERS))

                userFavoriteList.add(GithubUser(id, username, email, avatarUrl, company, location, publicRepos, following, followers))
            }
        }

        return userFavoriteList
    }

    fun mapCursorToObject(userFavoriteCursor: Cursor?): GithubUser {
        var githubUser = GithubUser()

        userFavoriteCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(GithubUserColumns._ID))
            val username = getString(getColumnIndexOrThrow(GithubUserColumns.USERNAME))
            val email = getString(getColumnIndexOrThrow(GithubUserColumns.EMAIL))
            val avatarUrl = getString(getColumnIndexOrThrow(GithubUserColumns.AVATAR_URL))
            val company = getString(getColumnIndexOrThrow(GithubUserColumns.COMPANY))
            val location = getString(getColumnIndexOrThrow(GithubUserColumns.LOCATION))
            val publicRepos = getInt(getColumnIndexOrThrow(GithubUserColumns.PUBLIC_REPOS))
            val following = getInt(getColumnIndexOrThrow(GithubUserColumns.FOLLOWING))
            val followers = getInt(getColumnIndexOrThrow(GithubUserColumns.FOLLOWERS))

            githubUser = GithubUser(id, username, email, avatarUrl, company, location, publicRepos, following, followers)
        }

        return githubUser
    }
}