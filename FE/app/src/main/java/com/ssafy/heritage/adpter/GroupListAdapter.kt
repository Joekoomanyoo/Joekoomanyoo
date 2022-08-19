package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.ItemGroupBinding


class GroupListAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<GroupListAdapter.GroupListViewHolder>() {

    var groupList = mutableListOf<GroupListResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupListViewHolder {
        val binding = ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupListViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    fun submitList(list: MutableList<GroupListResponse>) {
        groupList = list
        notifyDataSetChanged()
    }

    fun getItem(position: Int): GroupListResponse {
        return groupList[position]
    }

    inner class GroupListViewHolder(private val binding: ItemGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(position: Int) = with(binding) {
            val item = groupList[position]
            groupListResponse = item
//            if (item.groupAccessType == '1') {
//                ivLockOn.visibility = View.GONE
//            }
//            if (item.groupWithChild == 'N') {
//
//                tvKidsCheck.visibility = View.GONE
//
//                if (item.groupWithGlobal == 'N') {
//                    tvGlobalCheck.visibility = View.GONE
//                }
//            }
        }
    }
}