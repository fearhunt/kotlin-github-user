package com.example.kotlingithubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class GithubUserConnectionViewModel : ViewModel() {
    private val list: ArrayList<GithubUserConnection> = arrayListOf()

    val githubUserConnection = MutableLiveData<ArrayList<GithubUserConnection>>()

    fun setUserConnection(username: String, connection: String) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token " + BuildConfig.API_KEY)
        client.addHeader("User-Agent", "request")

        val url = "https://api.github.com/users/$username/$connection"

        client.get(url, object: AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                // called when response HTTP status is "200 OK"

                val res = String(responseBody)

                try {
                    val resArr = JSONArray(res)

                    for (i in 0 until resArr.length()) {
                        val resObj = resArr.getJSONObject(i)

                        val githubUserConnection = GithubUserConnection()

                        githubUserConnection.username = resObj.getString("login")
                        githubUserConnection.repos_url = resObj.getString("repos_url")
                        githubUserConnection.avatar_url = resObj.getString("avatar_url")
                        githubUserConnection.html_url = resObj.getString("html_url")

                        list.add(githubUserConnection)
                    }

                    githubUserConnection.postValue(list)
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

    fun getUser(): LiveData<ArrayList<GithubUserConnection>> {
        return githubUserConnection
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