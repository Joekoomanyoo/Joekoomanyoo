package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.dto.HeritageReview
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.data.remote.response.HeritageReviewListResponse
import com.ssafy.heritage.databinding.ItemGroupBinding
import com.ssafy.heritage.databinding.ItemReviewBinding

class HeritageReviewAdapter(private val listener: OnItemClickListener) : ListAdapter<HeritageReviewListResponse, HeritageReviewAdapter.ViewHolder>(
    DiffCallback()
){
    inner class ViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(data: HeritageReviewListResponse) = with(binding) {
            binding.apply {
                heritageReviewListResponse = data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<HeritageReviewListResponse>() {
        override fun areItemsTheSame(
            oldItem: HeritageReviewListResponse,
            newItem: HeritageReviewListResponse
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: HeritageReviewListResponse,
            newItem: HeritageReviewListResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

}