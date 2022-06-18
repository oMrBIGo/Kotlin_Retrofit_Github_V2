package com.nathit.kotlin_retrofit_github.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nathit.kotlin_retrofit_github.EventModel
import com.nathit.kotlin_retrofit_github.databinding.EventItemBinding

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
        holder.binding.typeTv.text = event_list[position].type
    }

    override fun getItemCount(): Int {
        return event_list.size
    }
}