package com.example.kotlingithubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlingithubuser.databinding.ItemRowGithubUsersBinding

class ListGithubUserAdapter : RecyclerView.Adapter<ListGithubUserAdapter.ListViewHolder>() {
    private var mData = ArrayList<GithubUser>()

    fun setData(items: ArrayList<GithubUser>) {
        mData = items
        notifyDataSetChanged()
    }

    fun clearData() {
        mData.clear()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowGithubUsersBinding.bind(itemView)

        fun bind(githubUser: GithubUser) {
            with(itemView) {
                Glide.with(context)
                    .load(githubUser.avatarUrl)
                    .apply(RequestOptions().override(72, 72))
                    .into(binding.imgItemPhoto)

                binding.tvItemUsername.text = githubUser.username
                binding.tvItemLocation.text = githubUser.location
                binding.tvItemCompany.text = githubUser.company
                binding.btnItemDetails.setOnClickListener {
                    val intent = Intent(context, GithubUserDetailActivity::class.java)
                    intent.putExtra(GithubUserDetailActivity.EXTRA_GITHUB_USER, githubUser)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): ListViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_github_users, viewGroup, false)
        return ListViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, index: Int) {
        holder.bind(mData[index])
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}
