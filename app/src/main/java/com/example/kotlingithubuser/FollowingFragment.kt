package com.example.kotlingithubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlingithubuser.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {
    private lateinit var adapter: ListGithubUserFollowerAdapter
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var githubUserConnectionViewModel: GithubUserConnectionViewModel

    companion object {
        @JvmStatic
        fun newInstance(username: String?) = FollowingFragment().apply {
            val fragment = FollowingFragment()
            val bundle = Bundle()

            bundle.putString("username", username)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFollowingBinding.inflate(layoutInflater)

        binding.rvGithubUserFollowing.setHasFixedSize(true)

        adapter = ListGithubUserFollowerAdapter()

        binding.rvGithubUserFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvGithubUserFollowing.adapter = adapter

        githubUserConnectionViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(GithubUserConnectionViewModel::class.java)

        showLoading(true)

        val username = arguments?.getString("username")

        binding.tvTitleConnection.text = "$username's Following"

        githubUserConnectionViewModel.setUserConnection(username.toString(), "following")
        githubUserConnectionViewModel.getUser().observe(this, Observer { githubUserConnection ->
            if (githubUserConnection != null) {
                adapter.setData(githubUserConnection)
                showLoading(false)
            }
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
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