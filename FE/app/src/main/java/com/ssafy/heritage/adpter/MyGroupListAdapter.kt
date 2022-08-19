package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import com.ssafy.heritage.databinding.ItemFoldingCellBinding

class MyGroupListAdapter() :
    RecyclerView.Adapter<MyGroupListAdapter.MyGroupListViewHolder>() {

    private var groupList = mutableListOf<MyGroupResponse>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyGroupListAdapter.MyGroupListViewHolder {
        val binding =
            ItemFoldingCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyGroupListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyGroupListAdapter.MyGroupListViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    fun submitList(list: MutableList<MyGroupResponse>) {
        groupList = list
        notifyDataSetChanged()
    }

    fun getItem(position: Int): MyGroupResponse {
        return groupList[position]
    }


    inner class MyGroupListViewHolder(private val binding: ItemFoldingCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                binding.foldingCell.toggle(false)
            }
        }

        fun bind(position: Int) = with(binding) {
            val item = groupList[position]
            tvName.text = item.groupName
            tvGroupDescription.text = item.groupDescription
            tvStartDate.text = item.groupStartDate
            tvEndDate.text = item.groupEndDate
            tvGroupRegion.text = item.groupRegion
        }
    }

}