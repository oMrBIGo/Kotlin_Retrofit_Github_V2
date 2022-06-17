package com.nathit.kotlin_retrofit_github.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nathit.kotlin_retrofit_github.FollowersModel
import com.nathit.kotlin_retrofit_github.R
import com.nathit.kotlin_retrofit_github.databinding.FollowersItemBinding

class FollowersAdapter(
    private val context: Context,
    private val follower_list: List<FollowersModel>
) :
    RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    class ViewHolder(val binding: FollowersItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FollowersModel) {
            binding.followersData = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FollowersItemBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(follower_list[position])

        Glide.with(context)
            .load(follower_list[position].avatar_url)
            .centerCrop()
            .placeholder(R.drawable.progress_anim)
            .error(R.drawable.progress_anim)
            .into(holder.binding.photoIv)

        if (follower_list[position].type == "User") {
            holder.binding.uIdTv.text =
                "ID:" + follower_list[position].id.toString() + " | " + "Member"
        } else if (follower_list[position].type == "Organization") {
            holder.binding.uIdTv.text =
                "ID:" + follower_list[position].id.toString() + " | " + "Admin"
        } else {
            holder.binding.uIdTv.text = "Found Data!"
        }

        holder.binding.btnHtml.setOnClickListener {
            val url = follower_list[position].html_url
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return follower_list.size
    }


}