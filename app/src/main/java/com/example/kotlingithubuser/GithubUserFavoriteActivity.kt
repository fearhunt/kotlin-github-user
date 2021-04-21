package com.example.kotlingithubuser

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlingithubuser.adapter.ListGithubUserFavoriteAdapter
import com.example.kotlingithubuser.databinding.ActivityGithubUserFavoriteBinding
import com.example.kotlingithubuser.db.DatabaseContract.GithubUserColumns.Companion.CONTENT_URI
import com.example.kotlingithubuser.helper.MappingHelper
import com.example.kotlingithubuser.entity.GithubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GithubUserFavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: ListGithubUserFavoriteAdapter
    private lateinit var binding: ActivityGithubUserFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User Favorite"

        binding.tvUserFavoriteNotFound.visibility = View.GONE

        binding.rvGithubUserFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvGithubUserFavorite.setHasFixedSize(true)
        adapter = ListGithubUserFavoriteAdapter()
        binding.rvGithubUserFavorite.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadGithubUserFavoriteAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadGithubUserFavoriteAsync()
        }
        else {
            savedInstanceState.getParcelableArrayList<GithubUser>(EXTRA_FAVORITE)?.also {
                adapter.listUserFavorite = it
            }
        }
    }

    private fun loadGithubUserFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility = View.VISIBLE

            val deferredUserFavorite = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val userFavorite = deferredUserFavorite.await()
            binding.progressBar.visibility = View.GONE

            if (userFavorite.size > 0) {
                binding.tvUserFavoriteNotFound.visibility = View.GONE
                adapter.listUserFavorite = userFavorite
            }
            else {
                binding.tvUserFavoriteNotFound.visibility = View.VISIBLE
                adapter.listUserFavorite = ArrayList()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_FAVORITE, adapter.listUserFavorite)
    }

    companion object {
        private const val EXTRA_FAVORITE = "extra_favorite"
    }
}