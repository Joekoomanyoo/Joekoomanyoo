package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.remote.response.HeritageReviewListResponse
import com.ssafy.heritage.databinding.ItemReviewBinding

class HeritageReviewAdapter(private val listener: OnItemClickListener, val userSeq: Int) :
    ListAdapter<HeritageReviewListResponse, HeritageReviewAdapter.ViewHolder>(
        DiffCallback()
    ) {
    inner class ViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imagebtnHeritageReviewDelete.setOnClickListener {
                listener.onItemClick(adapterPosition)

            }
        }

        fun bind(data: HeritageReviewListResponse) = with(binding) {
            binding.apply {
                heritageReviewListResponse = data
                if (binding.heritageReviewListResponse?.reviewImgUrl.toString() == "") {
                    binding.ivHeritageReviewImg.visibility = View.GONE
                } else {
                    binding.ivHeritageReviewImg.visibility = View.VISIBLE
                }

                if (userSeq != data.userSeq) {
                    imagebtnHeritageReviewDelete.visibility = View.GONE
                } else {
                    imagebtnHeritageReviewDelete.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
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