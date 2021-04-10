package com.example.kotlingithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var isDataFetched: Boolean = false

        supportActionBar?.title = "Github User App"

        binding.rvGithubUsers.setHasFixedSize(true)

        adapter = ListGithubUserAdapter()

        binding.rvGithubUsers.layoutManager = LinearLayoutManager(this)
        binding.rvGithubUsers.adapter = adapter

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        showLoading(true)

        mainViewModel.setUser()
        mainViewModel.getUser().observe(this, Observer { githubUser ->
            if (githubUser != null && !isDataFetched) {
                adapter.setData(githubUser)
                isDataFetched = true

                Handler(Looper.getMainLooper()).postDelayed({
                    recreate()
                }, 500)

                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
