package com.ssafy.heritage.adpter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.R
import com.ssafy.heritage.data.dto.Stamp
import com.ssafy.heritage.databinding.ItemBookBinding

class BookListAdapter() : ListAdapter<Stamp, BookListAdapter.ViewHolder>(DiffCallback()) {



    inner class ViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Stamp) = with(binding) {
//            stamp = data
//
//            // 내가 찾은 것들은 흐릿하게
//            if (data.found == 'Y') {
//                itemView.background = ContextCompat.getDrawable(itemView.context, R.color.black)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Stamp>() {
        override fun areItemsTheSame(oldItem: Stamp, newItem: Stamp): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Stamp, newItem: Stamp): Boolean {
            return oldItem == newItem
        }
    }
}