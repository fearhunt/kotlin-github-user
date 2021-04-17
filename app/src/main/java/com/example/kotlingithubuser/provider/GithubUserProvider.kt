package com.example.kotlingithubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.kotlingithubuser.db.DatabaseContract.AUTHORITY
import com.example.kotlingithubuser.db.DatabaseContract.GithubUserColumns.Companion.CONTENT_URI
import com.example.kotlingithubuser.db.DatabaseContract.GithubUserColumns.Companion.TABLE_NAME
import com.example.kotlingithubuser.db.GithubUserHelper

class GithubUserProvider : ContentProvider() {
    companion object {
        private const val GITHUB_USER = 1
        private const val GITHUB_USER_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var githubUserHelper: GithubUserHelper

        init {
            // content://com.example.kotlingithubuser/github_user
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, GITHUB_USER)

            // content://com.example.kotlingithubuser/github_user/id
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", GITHUB_USER_ID)
        }
    }

    override fun onCreate(): Boolean {
        githubUserHelper = GithubUserHelper.getInstance(context as Context)
        githubUserHelper.open()

        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (GITHUB_USER) {
            sUriMatcher.match(uri) -> githubUserHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val updated: Int = when (GITHUB_USER_ID) {
            sUriMatcher.match(uri) -> githubUserHelper.update(uri.lastPathSegment.toString(), values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (GITHUB_USER_ID) {
            sUriMatcher.match(uri) -> githubUserHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return when (sUriMatcher.match(uri)) {
            GITHUB_USER -> githubUserHelper.queryAll()
            GITHUB_USER_ID -> githubUserHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}