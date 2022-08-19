package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.dto.Keyword
import com.ssafy.heritage.databinding.ItemKeywordBinding
import com.ssafy.heritage.listener.KeywordListLongClickListener

class KeywordListAdapter : ListAdapter<Keyword, KeywordListAdapter.ViewHolder>(DiffCallback()) {

    lateinit var kywordListLongClickListener: KeywordListLongClickListener

    inner class ViewHolder(private val binding: ItemKeywordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Keyword) = with(binding) {
            name = data.myKeywordName

            itemView.setOnLongClickListener {
                kywordListLongClickListener.onClick(bindingAdapterPosition, data.myKeywordSeq)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemKeywordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Keyword>() {
        override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
            return oldItem == newItem
        }
    }
}