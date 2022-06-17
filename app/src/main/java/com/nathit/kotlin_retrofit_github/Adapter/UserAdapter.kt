package com.nathit.kotlin_retrofit_github.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nathit.kotlin_retrofit_github.Activity.FollowersActivity
import com.nathit.kotlin_retrofit_github.Activity.ReposActivity
import com.nathit.kotlin_retrofit_github.Adapter.UserAdapter.*
import com.nathit.kotlin_retrofit_github.R
import com.nathit.kotlin_retrofit_github.UserModel
import com.nathit.kotlin_retrofit_github.databinding.UserItemBinding

class UserAdapter(private val context: Context, private val users_List: List<UserModel>) :
    RecyclerView.Adapter<ViewHolder>() {




    class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UserModel) {
            binding.userData = data
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = UserItemBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(users_List[position])

        Glide.with(context)
            .load(users_List[position].avatar_url)
            .centerCrop()
            .placeholder(R.drawable.progress_anim)
            .error(R.drawable.progress_anim)
            .into(holder.binding.photoIv)

        if (users_List[position].type == "User") {
            holder.binding.uIdTv.text = "ID:"+users_List[position].id.toString() +" | " + "Member"
        } else if (users_List[position].type == "Organization") {
            holder.binding.uIdTv.text = "ID:"+users_List[position].id.toString() +" | " + "Admin"
        } else {
            holder.binding.uIdTv.text = "Found Data!"
        }
        holder.binding.btnHtml.setOnClickListener {
            val url = users_List[position].html_url
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }

        holder.binding.btnRepos.setOnClickListener {
            val intent = Intent(context,ReposActivity::class.java)
            intent.putExtra("repos_url", users_List[position].login)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        holder.binding.btnFollowers.setOnClickListener {
            val intent1 = Intent(context,FollowersActivity::class.java)
            intent1.putExtra("follower_url", users_List[position].login)
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent1)
        }

    }

    override fun getItemCount(): Int {
        return users_List.size
    }


}