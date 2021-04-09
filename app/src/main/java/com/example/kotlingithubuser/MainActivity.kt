package com.example.kotlingithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlingithubuser.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ListGithubUserAdapter
    private lateinit var binding: ActivityMainBinding

    private val list: ArrayList<GithubUser> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User App"

        binding.rvGithubUsers.setHasFixedSize(true)

        adapter = ListGithubUserAdapter()

        binding.rvGithubUsers.layoutManager = LinearLayoutManager(this)
        binding.rvGithubUsers.adapter = adapter

        addGithubUserData()
    }

    private fun addGithubUserData() {
        binding.progressBar.visibility = View.VISIBLE

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token " + BuildConfig.API_KEY)
        client.addHeader("User-Agent", "request")

        val url = "https://api.github.com/users"

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
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
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
//                    githubUser.company = if (resObj.getString("company") == null) resObj.getString("company") else "-"
                    githubUser.company = resObj.getString("company")
//                    githubUser.location = if (resObj.getString("location") == null) resObj.getString("location") else "-"
                    githubUser.location = resObj.getString("location")
                    githubUser.public_repos = resObj.getInt("public_repos")
                    githubUser.following = resObj.getInt("following")
                    githubUser.followers = resObj.getInt("followers")

                    list.add(githubUser)
                    adapter.setData(list)
                }
                catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                showFailureFetchError(statusCode, error)
            }
        })

        if (index == 29) {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun showFailureFetchError(statusCode: Int, error: Throwable) {
        val errorMessage = when (statusCode) {
            401 -> "$statusCode : Bad Request"
            403 -> "$statusCode : Forbidden"
            404 -> "$statusCode : Not Found"
            else -> "$statusCode : ${error.message}"
        }

        Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
    }

//    private fun showRecyclerList() {
//        binding.rvGithubUsers.layoutManager = LinearLayoutManager(this)
//        val listGithubUserAdapter = ListGithubUserAdapter(list)
//        binding.rvGithubUsers.adapter = listGithubUserAdapter
//    }
}
