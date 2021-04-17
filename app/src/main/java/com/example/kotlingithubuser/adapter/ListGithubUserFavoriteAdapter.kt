package com.example.kotlingithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlingithubuser.R
import com.example.kotlingithubuser.databinding.ItemRowGithubUserFavoriteBinding
import com.example.kotlingithubuser.entity.GithubUser

class ListGithubUserFavoriteAdapter : RecyclerView.Adapter<ListGithubUserFavoriteAdapter.ListViewHolder>() {
    var listUserFavorite = ArrayList<GithubUser>()
        set(listUserFavorite) {
            if (listUserFavorite.size > 0) {
                this.listUserFavorite.clear()
            }

            this.listUserFavorite.addAll(listUserFavorite)
            notifyDataSetChanged()
        }

    fun addItem(userFavorite: GithubUser) {
        this.listUserFavorite.add(userFavorite)
        notifyItemInserted(this.listUserFavorite.size - 1)
    }

    fun updateItem(index: Int, userFavorite: GithubUser) {
        this.listUserFavorite[index] = userFavorite
        notifyItemChanged(index, userFavorite)
    }

    fun removeItem(index: Int) {
        this.listUserFavorite.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, this.listUserFavorite.size)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowGithubUserFavoriteBinding.bind(itemView)

        fun bind(userFavorite: GithubUser) {
            Glide.with(binding.imgItemPhoto)
                .load(userFavorite.avatarUrl)
                .apply(RequestOptions().override(72, 72))
                .into(binding.imgItemPhoto)

            binding.tvItemUsername.text = userFavorite.username
            // More detail
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): ListViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_github_user_favorite, viewGroup, false)
        return ListViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, index: Int) {
        holder.bind(listUserFavorite[index])
    }

    override fun getItemCount(): Int {
        return this.listUserFavorite.size
    }
}