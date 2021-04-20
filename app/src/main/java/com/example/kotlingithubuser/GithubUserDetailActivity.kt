package com.example.kotlingithubuser

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlingithubuser.adapter.SectionsPagerAdapter
import com.example.kotlingithubuser.databinding.ActivityGithubUserDetailBinding
import com.example.kotlingithubuser.db.DatabaseContract.GithubUserColumns
import com.example.kotlingithubuser.entity.GithubUser
import com.example.kotlingithubuser.helper.MappingHelper
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GithubUserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGithubUserDetailBinding
    private lateinit var uriWithQuery: Uri
    private var githubUserFavorite: GithubUser? = null
    private val githubUser by lazy {
        intent.getParcelableExtra<GithubUser>(EXTRA_GITHUB_USER) as GithubUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = githubUser.username + "'s Detail"

        Log.d("dataDetail", githubUser.toString())

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

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                GlobalScope.launch(Dispatchers.Main) {
                    val deferredUserFavorite = async(Dispatchers.IO) {
                        val cursor = contentResolver.query(GithubUserColumns.CONTENT_URI, null, null, null, null)
                        MappingHelper.mapCursorToArrayList(cursor)
                    }

                    deferredUserFavorite.await()
                }
            }
        }

        contentResolver.registerContentObserver(GithubUserColumns.CONTENT_URI, true, myObserver)

        val uriQuery = if (githubUser.id != 0) githubUser.id else githubUser.username
        setUriAndFavoriteStatus(uriQuery.toString())
    }

    private fun addFavorite() {
        val values = ContentValues()
        values.put(GithubUserColumns.USERNAME, githubUser.username)
        values.put(GithubUserColumns.EMAIL, githubUser.email)
        values.put(GithubUserColumns.AVATAR_URL, githubUser.avatarUrl)
        values.put(GithubUserColumns.COMPANY, githubUser.company)
        values.put(GithubUserColumns.LOCATION, githubUser.location)
        values.put(GithubUserColumns.PUBLIC_REPOS, githubUser.publicRepos)
        values.put(GithubUserColumns.FOLLOWING, githubUser.following)
        values.put(GithubUserColumns.FOLLOWERS, githubUser.followers)

        contentResolver.insert(GithubUserColumns.CONTENT_URI, values)

        setUriAndFavoriteStatus(githubUser.username)

        Toast.makeText(this, getString(R.string.added_favorite, githubUser.username), Toast.LENGTH_LONG).show()
    }

    private fun deleteFavorite() {
        contentResolver.delete(uriWithQuery, null, null)

        setUriAndFavoriteStatus(githubUser.username)

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

    private fun setUriAndFavoriteStatus(uriQuery: String?) {
        uriWithQuery = Uri.parse("${GithubUserColumns.CONTENT_URI}/$uriQuery")
        val cursor = contentResolver.query(uriWithQuery, null, null, null, null)

        if (cursor != null) {
            try {
                githubUserFavorite = MappingHelper.mapCursorToObject(cursor)
                cursor.close()

                favoriteStatus(true)
            }
            catch (e: Exception) {
                Log.d("cursorStatus", "User not found on database")
                githubUserFavorite = null

                favoriteStatus(false)
            }
        }
        else {
            githubUserFavorite = null

            favoriteStatus(false)
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