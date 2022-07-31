package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.ItemGroupBinding

class GroupListAdapter(private val listener: OnItemClickListener) : ListAdapter<GroupListResponse, GroupListAdapter.ViewHolder>(DiffCallback()){

    inner class ViewHolder(private val binding: ItemGroupBinding) :
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(data: GroupListResponse) = with(binding){
            binding.apply {
                groupListResponse = data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<GroupListResponse>() {
        override fun areItemsTheSame(oldItem: GroupListResponse, newItem: GroupListResponse): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }


        override fun areContentsTheSame(oldItem: GroupListResponse, newItem: GroupListResponse): Boolean {
            return oldItem == newItem
        }

    }

}