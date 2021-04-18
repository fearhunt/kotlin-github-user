package com.example.kotlingithubuser

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlingithubuser.adapter.SectionsPagerAdapter
import com.example.kotlingithubuser.databinding.ActivityGithubUserDetailBinding
import com.example.kotlingithubuser.db.DatabaseContract.GithubUserColumns.Companion.AVATAR_URL
import com.example.kotlingithubuser.db.DatabaseContract.GithubUserColumns.Companion.CONTENT_URI
import com.example.kotlingithubuser.db.DatabaseContract.GithubUserColumns.Companion.USERNAME
import com.example.kotlingithubuser.entity.GithubUser
import com.example.kotlingithubuser.vm.GithubUserDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class GithubUserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGithubUserDetailBinding
    private lateinit var githubUserDetailViewModel: GithubUserDetailViewModel
    private lateinit var uriWithid: Uri
    private val githubUser by lazy {
        intent.getParcelableExtra<GithubUser>(EXTRA_GITHUB_USER) as GithubUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = githubUser.username + "'s Detail"

        Glide.with(binding.imgItemPhoto)
            .load(githubUser.avatarUrl)
            .apply(RequestOptions().override(100, 100))
            .into(binding.imgItemPhoto)

        binding.tvItemUsername.text = githubUser.username
        binding.tvItemLocation.text = githubUser.location
        binding.tvItemCompany.text = githubUser.company
        binding.tvItemFollowers.text = (githubUser.followers).toString()
        binding.tvItemFollowing.text = (githubUser.following).toString()
        binding.tvItemRepositories.text = (githubUser.publicRepos).toString()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.setUsername(githubUser.username)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        githubUserDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(GithubUserDetailViewModel::class.java)
        githubUserDetailViewModel.getUserFavorite().observe(this, Observer { isFavorite ->
            if (!isFavorite) {
                binding.fabFav.setOnClickListener {
                    addFavorite()
                }
            }
            else {
                binding.fabFav.setOnClickListener {
                    deleteFavorite()
                }
            }
        })
    }

    private fun addFavorite() {
        val values = ContentValues()
        values.put(USERNAME, githubUser.username)
        values.put(AVATAR_URL, githubUser.avatarUrl)

        contentResolver.insert(CONTENT_URI, values)

        Toast.makeText(this, getString(R.string.added_favorite, githubUser.username), Toast.LENGTH_LONG).show()
    }

    private fun deleteFavorite() {
        Toast.makeText(this, getString(R.string.deleted_favorite, githubUser.username), Toast.LENGTH_LONG).show()
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NewApi")
    private fun favoriteStatus(isFavorite: Boolean){
        if (!isFavorite) {
            binding.fabFav.setOnClickListener {
                addFavorite()
            }

            binding.fabFav.setImageDrawable(getDrawable(R.drawable.ic_heart_outline))
        }
        else {
            binding.fabFav.setOnClickListener {
                deleteFavorite()
            }

            binding.fabFav.setImageDrawable(getDrawable(R.drawable.ic_heart))
        }
    }

    companion object {
        const val EXTRA_GITHUB_USER = "extra_github_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.tab_text_1,
                R.string.tab_text_2
        )
    }
}