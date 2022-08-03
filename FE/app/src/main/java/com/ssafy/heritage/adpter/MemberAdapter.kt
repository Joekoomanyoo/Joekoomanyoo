package com.ssafy.heritage.adpter

import android.content.ClipData
import android.os.Build.VERSION_CODES.P
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.dto.Member
import com.ssafy.heritage.databinding.ItemMemberBinding

class MemberAdapter(private val listener: OnItemClickListener)
    :RecyclerView.Adapter<MemberAdapter.MemberViewHolder>(){

    private var memberList = mutableListOf<Member>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.MemberViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberAdapter.MemberViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    fun submitList(list: MutableList<Member>){
        memberList = list
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Member {
        return memberList[position]
    }

    inner class MemberViewHolder(private val binding: ItemMemberBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
        fun bind(position: Int)= with(binding){
            val item = memberList[position]
            member = item
        }
    }
}