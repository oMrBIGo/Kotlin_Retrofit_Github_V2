package com.nathit.kotlin_retrofit_github.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nathit.kotlin_retrofit_github.ReposModel
import com.nathit.kotlin_retrofit_github.databinding.ReposItemBinding
import java.text.SimpleDateFormat


class ReposAdapter(private val context: Context, private val repos_List: List<ReposModel>) :
    RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    class ViewHolder(val binding: ReposItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ReposModel) {
            binding.reposData = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ReposItemBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm a")

        val output_create_at: String =
            formatter.format(parser.parse(repos_List[position].created_at))
        val output_update_at: String =
            formatter.format(parser.parse(repos_List[position].updated_at))
        val output_pushed_at: String =
            formatter.format(parser.parse(repos_List[position].pushed_at))

        holder.bind(repos_List[position])


        holder.binding.createTv.text = output_create_at
        holder.binding.updateTv.text = output_update_at
        holder.binding.pushedTv.text = output_pushed_at

        holder.binding.clickToGithub.setOnClickListener {
            val url = repos_List[position].html_url
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return repos_List.size
    }
}


