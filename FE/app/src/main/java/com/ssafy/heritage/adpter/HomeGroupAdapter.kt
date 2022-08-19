package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.ItemHomeGroupBinding
import com.ssafy.heritage.listener.FeedListClickListener

class HomeGroupAdapter :
    ListAdapter<GroupListResponse, HomeGroupAdapter.ViewHolder>(DiffCallback()) {

    lateinit var feedListClickListener: FeedListClickListener

    inner class ViewHolder(private val binding: ItemHomeGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GroupListResponse) = with(binding) {
            group = data
//            if (data.hashtag.isNotEmpty()) {
//                binding.tvHashtag1.text = "# " + data.hashtag[0]
//                if (data.hashtag.size > 1) {
//                    binding.tvHashtag2.text = "# " + data.hashtag[1]
//                } else {
//                    binding.tvHashtag2.text = ""
//                }
//            } else {
//                binding.tvHashtag1.text = ""
//                binding.tvHashtag2.text = ""
//            }
//            binding.root.setOnClickListener {
//                feedListClickListener.onClick(bindingAdapterPosition, data, ivFeed)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<GroupListResponse>() {
        override fun areItemsTheSame(
            oldItem: GroupListResponse,
            newItem: GroupListResponse
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: GroupListResponse,
            newItem: GroupListResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}