package com.example.kotlingithubuser

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlingithubuser.adapter.ListGithubUserAdapter
import com.example.kotlingithubuser.databinding.ActivityMainBinding
import com.example.kotlingithubuser.vm.MainViewModel

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
                    adapter.clearData()
                    mainViewModel.setUser(query)
                }

                hideKeyboard(binding.root)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.progressBar.visibility = View.GONE

        binding.rvGithubUsers.setHasFixedSize(true)
        adapter = ListGithubUserAdapter()
        binding.rvGithubUsers.layoutManager = LinearLayoutManager(this)
        binding.rvGithubUsers.adapter = adapter

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.getUser().observe(this, Observer { githubUser ->
            if (githubUser != null) {
                adapter.setData(githubUser)
                Log.i("dataGithubUser", githubUser.toString())
            }
        })
        mainViewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                showLoading(true)
            }
            else {
                showLoading(false)
            }
        })
        mainViewModel.errorMessage.observe(this, Observer {
            var errorMessage: String = it

            if (errorMessage != "") {
                if (!(it.contains(":"))) {
                    errorMessage = getString(R.string.user_not_found, it)
                }

                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
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

    private fun hideKeyboard(view: View){
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        else if (item.itemId == R.id.action_user_favorite) {
            val mIntent = Intent(this@MainActivity, GithubUserFavoriteActivity::class.java)
            startActivity(mIntent)
        }

        return super.onOptionsItemSelected(item)
    }
}
