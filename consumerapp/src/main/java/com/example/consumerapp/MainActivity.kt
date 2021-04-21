package com.example.consumerapp

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.adapter.ListGithubUserAdapter
import com.example.consumerapp.databinding.ActivityMainBinding
import com.example.consumerapp.db.DatabaseContract.GithubUserColumns.Companion.CONTENT_URI
import com.example.consumerapp.helper.MappingHelper
import com.example.consumerapp.entity.GithubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ListGithubUserAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Consumer App User Favorite"

        binding.rvGithubUsers.layoutManager = LinearLayoutManager(this)
        binding.rvGithubUsers.setHasFixedSize(true)
        adapter = ListGithubUserAdapter()
        binding.rvGithubUsers.adapter = adapter

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
                adapter.listUserFavorite = userFavorite
                Log.d("userFavorite", userFavorite.toString())
            }
            else {
                adapter.listUserFavorite = ArrayList()
                Toast.makeText(this@MainActivity, "No user favorite data", Toast.LENGTH_LONG).show()
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