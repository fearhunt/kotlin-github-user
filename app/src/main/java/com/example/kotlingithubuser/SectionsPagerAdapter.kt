package com.example.kotlingithubuser

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    private var username: String? = null

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ConnectionFragment.newInstance(username, "followers")
            1 -> fragment = ConnectionFragment.newInstance(username, "following")
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

    fun setUsername(username: String?) {
        this.username = username
    }
}