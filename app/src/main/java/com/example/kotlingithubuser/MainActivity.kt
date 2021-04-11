package com.example.kotlingithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlingithubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ListGithubUserAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User App"

        binding.svUser.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchUser(query, "https://api.github.com/search/users")
                }
                else {
                    fetchUser()
                }

                binding.svUser.clearFocus()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.rvGithubUsers.setHasFixedSize(true)

        adapter = ListGithubUserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvGithubUsers.layoutManager = LinearLayoutManager(this)
        binding.rvGithubUsers.adapter = adapter

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        fetchUser()
    }

    private fun fetchUser(query: String = "", url: String = "https://api.github.com/users") {
        showLoading(true)

        mainViewModel.setUser(query, url)
        mainViewModel.getUser().observe(this, Observer { githubUser ->
            if (githubUser != null) {
                adapter.setData(githubUser)
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
