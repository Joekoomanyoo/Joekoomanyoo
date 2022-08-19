package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.dto.Noti
import com.ssafy.heritage.databinding.ItemNotiBinding

class NotiListAdapter : ListAdapter<Noti, NotiListAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemNotiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Noti) = with(binding) {
            noti = data

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNotiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Noti>() {
        override fun areItemsTheSame(oldItem: Noti, newItem: Noti): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Noti, newItem: Noti): Boolean {
            return oldItem == newItem
        }
    }
}