package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.remote.response.FeedListResponse
import com.ssafy.heritage.databinding.ItemFeedBinding
import com.ssafy.heritage.listener.FeedListClickListener

class FeedListAdapter() :
    ListAdapter<FeedListResponse, FeedListAdapter.ViewHolder>(DiffCallback()) {

    lateinit var feedListClickListener: FeedListClickListener

    inner class ViewHolder(private val binding: ItemFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: FeedListResponse) = with(binding) {
            binding.apply {
                feedListResponse = data

                ViewCompat.setTransitionName(ivFeed, "feed$bindingAdapterPosition")

                binding.root.setOnClickListener {
                    feedListClickListener.onClick(bindingAdapterPosition, data, ivFeed)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<FeedListResponse>() {
        override fun areItemsTheSame(
            oldItem: FeedListResponse,
            newItem: FeedListResponse
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: FeedListResponse,
            newItem: FeedListResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

//    fun getItem(position: Int): FeedListResponse {
//        return feedList[position]
//    }
}