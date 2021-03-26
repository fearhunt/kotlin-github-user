package com.example.kotlingithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class GithubUserDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_GITHUB_USER = "extra_github_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user_detail)

        val githubUser = intent.getParcelableExtra<GithubUser>(EXTRA_GITHUB_USER) as GithubUser
        val img = resources.getIdentifier(githubUser.avatar, "drawable", packageName)

        supportActionBar!!.title = githubUser.username + "'s Detail"

        val tvName: TextView = findViewById(R.id.tv_item_name)
        val tvUsername: TextView = findViewById(R.id.tv_item_username)
        val imgPhoto: ImageView = findViewById(R.id.img_item_photo)
        val tvLocation: TextView = findViewById(R.id.tv_item_location)
        val tvCompany: TextView = findViewById(R.id.tv_item_company)
        val tvRepositories: TextView = findViewById(R.id.tv_item_repositories)
        val tvFollowers: TextView = findViewById(R.id.tv_item_followers)
        val tvFollowing: TextView = findViewById(R.id.tv_item_following)

        tvName.text = githubUser.name
        tvUsername.text = githubUser.username
        imgPhoto.setImageResource(img)
        tvLocation.text = githubUser.location
        tvCompany.text = githubUser.company
        tvFollowers.text = (githubUser.follower).toString()
        tvFollowing.text = (githubUser.following).toString()
        tvRepositories.text = (githubUser.repository).toString()
    }
}