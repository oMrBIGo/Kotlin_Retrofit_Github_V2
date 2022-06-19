package com.nathit.kotlin_retrofit_github.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nathit.kotlin_retrofit_github.BASE_URL
import com.nathit.kotlin_retrofit_github.EventModel
import com.nathit.kotlin_retrofit_github.R
import com.nathit.kotlin_retrofit_github.databinding.EventItemBinding
import java.text.SimpleDateFormat

class EventsAdapter(
    private val context: Context,
    private val event_list: List<EventModel>
) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    class ViewHolder(val binding: EventItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: EventModel) {
            binding.eventData = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = EventItemBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(event_list[position])

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm a")
        val output_create_at: String =
            formatter.format(parser.parse(event_list[position].created_at))
        holder.binding.createTv.text = output_create_at

        if (event_list[position].type == "PullRequestReviewCommentEvent") {
            holder.binding.typeTv.text = "PullRequestEvent"
        } else {
            holder.binding.typeTv.text = event_list[position].type
        }
        holder.binding.typeTv

        Glide.with(context)
            .load(event_list[position].actor.avatar_url)
            .centerCrop()
            .placeholder(R.drawable.progress_anim)
            .error(R.drawable.progress_anim)
            .into(holder.binding.avatarIv)

        holder.binding.btnHtml.setOnClickListener {
            val url = event_list[position].actor.login
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.data = Uri.parse("https://github.com/$url")
            context.startActivity(intent)
        }

        holder.binding.btnRepos.setOnClickListener {
            val url = event_list[position].repo.name
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.data = Uri.parse("https://github.com/$url")
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return event_list.size
    }
}