package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.ItemHeritageBinding
import com.ssafy.heritage.listener.HeritageListClickListener

class HeritageListAdapter : ListAdapter<Heritage, HeritageListAdapter.ViewHolder>(DiffCallback()) {

    lateinit var heritageListClickListener: HeritageListClickListener

    inner class ViewHolder(private val binding: ItemHeritageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Heritage) = with(binding) {
            heritage = data

            itemView.setOnClickListener {
                heritageListClickListener.onClick(bindingAdapterPosition, data, ivHeritage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHeritageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Heritage>() {
        override fun areItemsTheSame(oldItem: Heritage, newItem: Heritage): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Heritage, newItem: Heritage): Boolean {
            return oldItem == newItem
        }
    }
}