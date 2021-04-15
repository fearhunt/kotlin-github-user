package com.example.kotlingithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlingithubuser.adapter.ListGithubUserConnectionAdapter
import com.example.kotlingithubuser.databinding.FragmentConnectionBinding
import com.example.kotlingithubuser.vm.GithubUserConnectionViewModel

class ConnectionFragment : Fragment() {
    private lateinit var adapter: ListGithubUserConnectionAdapter
    private lateinit var binding: FragmentConnectionBinding
    private lateinit var githubUserConnectionViewModel: GithubUserConnectionViewModel

    companion object {
        @JvmStatic
        fun newInstance(username: String?, connectionType: String?) = ConnectionFragment().apply {
            val fragment = ConnectionFragment()
            val bundle = Bundle()

            bundle.putString("username", username)
            bundle.putString("connectionType", connectionType)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConnectionBinding.inflate(layoutInflater)

        val username = arguments?.getString("username")
        val connectionType = arguments?.getString("connectionType")

        if (connectionType == "followers") {
            binding.tvTitleConnection.text = getString(R.string.followers_list, username)
        }
        else if (connectionType == "following") {
            binding.tvTitleConnection.text = getString(R.string.following_list, username)
        }

        binding.rvGithubUserConnection.setHasFixedSize(true)

        adapter = ListGithubUserConnectionAdapter()

        binding.rvGithubUserConnection.layoutManager = LinearLayoutManager(activity)
        binding.rvGithubUserConnection.adapter = adapter

        adapter.clearData()

        githubUserConnectionViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            GithubUserConnectionViewModel::class.java)
        githubUserConnectionViewModel.setUserConnection(username.toString(), connectionType.toString())
        githubUserConnectionViewModel.getUser().observe(this, Observer { githubUserConnection ->
            if (githubUserConnection != null) {
                adapter.setData(githubUserConnection)
            }
        })
        githubUserConnectionViewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                showLoading(true)
            }
            else {
                showLoading(false)
            }
        })
        githubUserConnectionViewModel.errorMessage.observe(this, Observer {
            var errorMessage: String = it

            if (errorMessage != "") {
                if (!(it.contains(":"))) {
                    errorMessage = getString(R.string.user_not_found, it)
                }

                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}