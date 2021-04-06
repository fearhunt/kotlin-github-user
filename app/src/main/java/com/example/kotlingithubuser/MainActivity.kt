package com.example.kotlingithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    private lateinit var rvGithubUsers: RecyclerView
    private var list: ArrayList<GithubUser> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Github User App"

        rvGithubUsers = findViewById(R.id.rv_github_users)
        rvGithubUsers.setHasFixedSize(true)

        addGithubUserData()
        showRecyclerList()
    }

    private fun addGithubUserData() {
        try {
            val obj = JSONObject(loadJSONFromAsset())
            val userArray = obj.getJSONArray("users")

            for (index in 0 until userArray.length()) {
                val user = userArray.getJSONObject(index)
                val githubUser = GithubUser()

                githubUser.name = user.getString("name")
                githubUser.username = user.getString("username")
                githubUser.avatar = user.getString("avatar")
                githubUser.company = user.getString("company")
                githubUser.location = user.getString("location")
                githubUser.repository = user.getInt("repository")
                githubUser.following = user.getInt("following")
                githubUser.follower = user.getInt("follower")
                githubUser.follower = user.getInt("follower")

                list.add(githubUser)
            }
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun loadJSONFromAsset(): String {
        val json: String?

        try {
            val fileStream = assets.open("githubuser.json")
            val size = fileStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8

            fileStream.read(buffer)
            fileStream.close()

            json = String(buffer, charset)
        }
        catch (e: IOException) {
            e.printStackTrace()
            return ""
        }

        return json
    }

    private fun showRecyclerList() {
        rvGithubUsers.layoutManager = LinearLayoutManager(this)
        val listGithubUserAdapter = ListGithubUserAdapter(list)
        rvGithubUsers.adapter = listGithubUserAdapter
    }
}
