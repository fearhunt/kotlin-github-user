package com.example.kotlingithubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListGithubUserAdapter(private val listGithubUsers: ArrayList<GithubUser>) : RecyclerView.Adapter<ListGithubUserAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvUsername: TextView = itemView.findViewById(R.id.tv_item_username)
        val tvLocation: TextView = itemView.findViewById(R.id.tv_item_location)
        val tvCompany: TextView = itemView.findViewById(R.id.tv_item_company)
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val btnDetail: Button = itemView.findViewById(R.id.btn_item_details)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListGithubUserAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_github_users, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, index: Int) {
        val githubUser = listGithubUsers[index]
        val context = holder.itemView.context
        val img = context.resources.getIdentifier(githubUser.avatar, "drawable", context.packageName)

        holder.tvName.text = githubUser.name
        holder.tvUsername.text = githubUser.username
        holder.imgPhoto.setImageResource(img)
        holder.tvLocation.text = githubUser.location
        holder.tvCompany.text = githubUser.company
        holder.btnDetail.setOnClickListener {
            val intent = Intent(context, GithubUserDetailActivity::class.java)
            intent.putExtra(GithubUserDetailActivity.EXTRA_GITHUB_USER, githubUser)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listGithubUsers.size
    }

}
