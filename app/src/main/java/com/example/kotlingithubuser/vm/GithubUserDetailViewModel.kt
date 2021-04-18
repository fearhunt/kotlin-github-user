package com.example.kotlingithubuser.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GithubUserDetailViewModel() : ViewModel() {
    private val isFavorite = MutableLiveData<Boolean>(false)

    fun setUser(query: String) {

    }

    fun getUserFavorite(): LiveData<Boolean> {
        return isFavorite
    }

    private fun showFailureFetchError(statusCode: Int, error: Throwable) {
        val errorMessage = when (statusCode) {
            401 -> "$statusCode : Bad Request"
            403 -> "$statusCode : Forbidden"
            404 -> "$statusCode : Not Found"
            else -> "$statusCode : ${error.message}"
        }

        Log.e("Exception", errorMessage)
    }
}