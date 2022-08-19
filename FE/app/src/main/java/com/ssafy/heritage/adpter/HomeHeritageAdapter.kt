package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.databinding.ItemHomeBinding
import com.ssafy.heritage.listener.HomeCategoryListClickListener
import com.ssafy.heritage.util.CategoryConverter

class HomeHeritageAdapter : ListAdapter<String, HomeHeritageAdapter.ViewHolder>(DiffCallback()) {

    lateinit var homeCategoryListClickListener: HomeCategoryListClickListener

    inner class ViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) = with(binding) {
            src = CategoryConverter.imageMap[data]
            name = data

            itemView.setOnClickListener {
                homeCategoryListClickListener.onClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}