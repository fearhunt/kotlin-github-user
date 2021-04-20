package com.example.kotlingithubuser.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.kotlingithubuser.db.DatabaseContract.GithubUserColumns

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbgithubuser"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_GITHUB_USER = "" +
            "CREATE TABLE ${GithubUserColumns.TABLE_NAME}" +
            "(${GithubUserColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${GithubUserColumns.USERNAME} TEXT NOT NULL," +
            "${GithubUserColumns.EMAIL} TEXT NOT NULL," +
            "${GithubUserColumns.AVATAR_URL} TEXT NOT NULL," +
            "${GithubUserColumns.COMPANY} TEXT NOT NULL," +
            "${GithubUserColumns.LOCATION} TEXT NOT NULL," +
            "${GithubUserColumns.PUBLIC_REPOS} TEXT NOT NULL," +
            "${GithubUserColumns.FOLLOWING} TEXT NOT NULL," +
            "${GithubUserColumns.FOLLOWERS} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_GITHUB_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${GithubUserColumns.TABLE_NAME}")
        onCreate(db)
    }
}