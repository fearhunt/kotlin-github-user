package com.example.kotlingithubuser.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlingithubuser.BuildConfig
import com.example.kotlingithubuser.model.GithubUser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {
    private val list: ArrayList<GithubUser> = arrayListOf()

    private val githubUsers = MutableLiveData<ArrayList<GithubUser>>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun setUser(query: String) {
        isLoading.postValue(true)

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token ${BuildConfig.API_KEY}")
        client.addHeader("User-Agent", "request")

        val url = "https://api.github.com/search/users?q=$query"

        client.get(url, object: AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                // called when response HTTP status is "200 OK"

                val res = String(responseBody)

                try {
                    val resObjSearch = JSONObject(res)

                    val resArr = resObjSearch.getJSONArray("items")
                    val resTotalCount = resObjSearch.getInt("total_count")

                    if (resTotalCount > 0) {
                        for (i in 0 until resArr.length()) {
                            val resObj = resArr.getJSONObject(i)

                            val username = resObj.getString("login")

                            fetchGithubUserDataDetail(username, client, i, resTotalCount)
                        }
                    }
                    else {
                        Log.e("Error", "User not found")
                        isLoading.postValue(false)
                        errorMessage.postValue(query)
                    }
                }
                catch (e: Exception) {
                    Log.e("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                showFailureFetchError(statusCode, error)
            }
        })
    }

    private fun fetchGithubUserDataDetail(username: String, client: AsyncHttpClient, index: Int, totalCount: Int) {
        val detailProfileURL = "https://api.github.com/users/$username"

        client.get(detailProfileURL, object: AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                // called when response HTTP status is "200 OK"

                val res = String(responseBody)

                try {
                    val resObj = JSONObject(res)

                    val githubUser = GithubUser()

                    githubUser.username = resObj.getString("login")
                    githubUser.email = resObj.getString("email")
                    githubUser.avatarUrl = resObj.getString("avatar_url")
                    githubUser.company = if (resObj.getString("company") != "null") resObj.getString("company") else "-"
                    githubUser.location = if (resObj.getString("location") != "null") resObj.getString("location") else "-"
                    githubUser.publicRepos = resObj.getInt("public_repos")
                    githubUser.following = resObj.getInt("following")
                    githubUser.followers = resObj.getInt("followers")

                    list.add(githubUser)

                    if ((index + 1) == totalCount) {
                        githubUsers.postValue(list)
                        isLoading.postValue(false)
                    }
                }
                catch (e: Exception) {
                    Log.e("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                showFailureFetchError(statusCode, error)
            }
        })
    }

    fun getUser(): LiveData<ArrayList<GithubUser>> {
        return githubUsers
    }

    private fun showFailureFetchError(statusCode: Int, error: Throwable) {
        val errorMessage = when (statusCode) {
            401 -> "$statusCode : Bad Request"
            403 -> "$statusCode : Forbidden"
            404 -> "$statusCode : Not Found"
            else -> "$statusCode : ${error.message}"
        }

        Log.e("Exception", errorMessage)
        isLoading.postValue(false)
        this.errorMessage.postValue(errorMessage)
    }
}