package com.example.kotlingithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlingithubuser.R
import com.example.kotlingithubuser.databinding.ItemRowGithubUserConnectionBinding
import com.example.kotlingithubuser.entity.GithubUserConnection

class ListGithubUserConnectionAdapter : RecyclerView.Adapter<ListGithubUserConnectionAdapter.ListViewHolder>() {
    private var mData = ArrayList<GithubUserConnection>()

    fun setData(items: ArrayList<GithubUserConnection>) {
        mData = items
        notifyDataSetChanged()
    }

    fun clearData() {
        mData.clear()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowGithubUserConnectionBinding.bind(itemView)

        fun bind(githubUserConnection: GithubUserConnection) {
            with(itemView) {
                Glide.with(context)
                    .load(githubUserConnection.avatarUrl)
                    .apply(RequestOptions().override(48, 48))
                    .into(binding.imgItemPhoto)

                binding.tvItemUsername.text = githubUserConnection.username
                binding.tvItemRepositories.text = githubUserConnection.reposUrl
                binding.tvItemUrl.text = githubUserConnection.htmlUrl
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): ListViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_github_user_connection, viewGroup, false)
        return ListViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, index: Int) {
        holder.bind(mData[index])
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}
