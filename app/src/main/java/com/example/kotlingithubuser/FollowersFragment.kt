package com.example.kotlingithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlingithubuser.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {
    private lateinit var adapter: ListGithubUserFollowerAdapter
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var githubUserConnectionViewModel: GithubUserConnectionViewModel

    companion object {
        @JvmStatic
        fun newInstance(username: String?) = FollowersFragment().apply {
            val fragment = FollowersFragment()
            val bundle = Bundle()

            bundle.putString("username", username)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFollowersBinding.inflate(layoutInflater)

        binding.rvGithubUserFollowers.setHasFixedSize(true)

        adapter = ListGithubUserFollowerAdapter()

        binding.rvGithubUserFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvGithubUserFollowers.adapter = adapter

        githubUserConnectionViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(GithubUserConnectionViewModel::class.java)

        showLoading(true)

        val username = arguments?.getString("username")

        binding.tvTitleConnection.text = username + " " + getString(R.string.followers_list)

        githubUserConnectionViewModel.setUserConnection(username.toString(), "followers")
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