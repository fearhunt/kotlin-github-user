package com.example.kotlingithubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlingithubuser.databinding.ItemRowGithubUsersBinding

class ListGithubUserAdapter : RecyclerView.Adapter<ListGithubUserAdapter.ListViewHolder>() {
    private val mData = ArrayList<GithubUser>()

    fun setData(items: ArrayList<GithubUser>) {
        mData.clear()
        mData.addAll(items)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val tvUsername: TextView = itemView.findViewById(R.id.tv_item_username)
//        val tvLocation: TextView = itemView.findViewById(R.id.tv_item_location)
//        val tvCompany: TextView = itemView.findViewById(R.id.tv_item_company)
//        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
//        val btnDetail: Button = itemView.findViewById(R.id.btn_item_details)

        private val binding = ItemRowGithubUsersBinding.bind(itemView)
        fun bind(githubUser: GithubUser) {
            with(itemView) {
                Glide.with(context)
                    .load(githubUser.avatar_url)
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
//        val githubUser = listGithubUsers[index]
//        val context = holder.itemView.context
//
//        Glide.with(holder.itemView.context)
//            .load(githubUser.avatar_url)
//            .apply(RequestOptions().override(72, 72))
//            .into(holder.imgPhoto)
//
//        holder.tvUsername.text = githubUser.username
//        holder.tvLocation.text = githubUser.location
//        holder.tvCompany.text = githubUser.company
//        holder.btnDetail.setOnClickListener {
//            val intent = Intent(context, GithubUserDetailActivity::class.java)
//            intent.putExtra(GithubUserDetailActivity.EXTRA_GITHUB_USER, githubUser)
//            context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

}
