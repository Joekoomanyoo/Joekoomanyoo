package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.remote.request.GroupSchedule
import com.ssafy.heritage.databinding.ItemGroupScheduleBinding
import com.ssafy.heritage.listener.GroupScheduleListClickListener

class GroupScheduleListAdapter :
    ListAdapter<GroupSchedule, GroupScheduleListAdapter.ViewHolder>(DiffCallback()) {

    lateinit var groupScheduleListClickListener: GroupScheduleListClickListener

    inner class ViewHolder(private val binding: ItemGroupScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GroupSchedule) = with(binding) {
            schedule = data

            btnDelete.setOnClickListener {
                groupScheduleListClickListener.onClick(
                    bindingAdapterPosition,
                    data.gsSeq
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGroupScheduleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<GroupSchedule>() {
        override fun areItemsTheSame(oldItem: GroupSchedule, newItem: GroupSchedule): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: GroupSchedule, newItem: GroupSchedule): Boolean {
            return oldItem == newItem
        }
    }
}