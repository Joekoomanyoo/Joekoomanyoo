package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.ItemGroupBinding


class GroupListAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<GroupListAdapter.GroupListViewHolder>() {

    //    inner class ViewHolder(private val binding: ItemGroupBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        init {
//            binding.root.setOnClickListener {
//                listener.onItemClick(adapterPosition)
//            }
//        }
//
//        fun bind(data: GroupListResponse) = with(binding) {
//            groupListResponse = data
//            if (data.groupAccessType == '1') {
//                ivLockOn.visibility = View.GONE
//            }
//            if (data.childJoin == 'N') {
//                tvKidsCheck.visibility = View.GONE
//            }
//            if (data.globalJoin == 'N') {
//                tvGlobalCheck.visibility = View.GONE
//            }
//
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            ItemGroupBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//    class DiffCallback : DiffUtil.ItemCallback<GroupListResponse>() {
//        override fun areItemsTheSame(
//            oldItem: GroupListResponse,
//            newItem: GroupListResponse
//        ): Boolean {
//            return oldItem.hashCode() == newItem.hashCode()
//        }
//
//
//        override fun areContentsTheSame(
//            oldItem: GroupListResponse,
//            newItem: GroupListResponse
//        ): Boolean {
//            return oldItem == newItem
//        }
//
//    }

    private var groupList = mutableListOf<GroupListResponse>()

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
            if (item.groupAccessType == '1') {
                ivLockOn.visibility = View.GONE
            }
            if (item.childJoin == 'N') {
                tvKidsCheck.visibility = View.GONE

                if (item.globalJoin == 'N') {
                    tvGlobalCheck.visibility = View.GONE
                }
            }
        }
    }
}