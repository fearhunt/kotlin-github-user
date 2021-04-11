package com.example.kotlingithubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {
    private val list: ArrayList<GithubUser> = arrayListOf()

    val githubUsers = MutableLiveData<ArrayList<GithubUser>>()

    fun setUser(query: String, url: String) {
        val client = AsyncHttpClient()
        val params = RequestParams()
        client.addHeader("Authorization", "token " + BuildConfig.API_KEY)
        client.addHeader("User-Agent", "request")

        if (query != "") {
            params.put("q", query)
        }

        client.get(url, object: AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                // called when response HTTP status is "200 OK"

                val res = String(responseBody)

                try {
                    val resArr = JSONArray(res)

                    for (i in 0 until resArr.length()) {
                        val resObj = resArr.getJSONObject(i)

                        val username = resObj.getString("login")

                        fetchGithubUserDataDetail(username, client, i)
                    }
                }
                catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                showFailureFetchError(statusCode, error)
            }
        })
    }

    private fun fetchGithubUserDataDetail(username: String, client: AsyncHttpClient, index: Int) {
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
                    githubUser.avatar_url = resObj.getString("avatar_url")
                    githubUser.company = if (resObj.getString("company") != "null") resObj.getString("company") else "-"
                    githubUser.location = if (resObj.getString("location") != "null") resObj.getString("location") else "-"
                    githubUser.public_repos = resObj.getInt("public_repos")
                    githubUser.following = resObj.getInt("following")
                    githubUser.followers = resObj.getInt("followers")

                    list.add(githubUser)

                    if (index == 29) {
                        githubUsers.postValue(list)
                    }
                }
                catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
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

        Log.d("Exception", errorMessage)
    }
}