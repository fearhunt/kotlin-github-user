package com.example.kotlingithubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlingithubuser.databinding.ItemRowGithubUserFollowingBinding

class ListGithubUserFollowingAdapter : RecyclerView.Adapter<ListGithubUserFollowingAdapter.ListViewHolder>() {
    private val mData = ArrayList<GithubUserConnection>()

    fun setData(items: ArrayList<GithubUserConnection>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowGithubUserFollowingBinding.bind(itemView)

        fun bind(githubUserConnection: GithubUserConnection) {
            with(itemView) {
                Glide.with(context)
                    .load(githubUserConnection.avatar_url)
                    .apply(RequestOptions().override(48, 48))
                    .into(binding.imgItemPhoto)

                binding.tvItemUsername.text = githubUserConnection.username
                binding.tvItemRepositories.text = githubUserConnection.repos_url
                binding.tvItemUrl.text = githubUserConnection.html_url
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): ListViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_github_user_following, viewGroup, false)
        return ListViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, index: Int) {
        holder.bind(mData[index])
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}
