package com.example.kotlingithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlingithubuser.databinding.ActivityGithubUserDetailBinding

class GithubUserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGithubUserDetailBinding

    companion object {
        const val EXTRA_GITHUB_USER = "extra_github_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val githubUser = intent.getParcelableExtra<GithubUser>(EXTRA_GITHUB_USER) as GithubUser

        supportActionBar?.title = githubUser.username + "'s Detail"

        Glide.with(binding.imgItemPhoto)
            .load(githubUser.avatar_url)
            .apply(RequestOptions().override(72, 72))
            .into(binding.imgItemPhoto)

        binding.tvItemUsername.text = githubUser.username
        binding.tvItemLocation.text = githubUser.location
        binding.tvItemCompany.text = githubUser.company
        binding.tvItemFollowers.text = (githubUser.followers).toString()
        binding.tvItemFollowing.text = (githubUser.following).toString()
        binding.tvItemRepositories.text = (githubUser.public_repos).toString()
    }
}