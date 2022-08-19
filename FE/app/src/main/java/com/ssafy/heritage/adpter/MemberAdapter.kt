package com.ssafy.heritage.adpter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.dto.Member
import com.ssafy.heritage.databinding.ItemMemberBinding
import com.ssafy.heritage.listener.MemberClickListener


class MemberAdapter:ListAdapter<Member, MemberAdapter.ViewHolder>(DiffCallback()){

    lateinit var memberClickListener: MemberClickListener
    inner class ViewHolder(private val binding: ItemMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Member)= with(binding){
            member = data
            Log.d("___MemberAdapter", data.toString())
            itemView.setOnClickListener {
                memberClickListener.onClick(bindingAdapterPosition, data, ivMemberImg)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Member>() {
        override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
//class MemberAdapter(private val listener: OnItemClickListener)
//    :RecyclerView.Adapter<MemberAdapter.MemberViewHolder>(){
//
//    private var memberList = mutableListOf<Member>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.MemberViewHolder {
//        val binding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MemberViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MemberAdapter.MemberViewHolder, position: Int) {
//        holder.bind(position)
//    }
//
//    override fun getItemCount(): Int {
//        return memberList.size
//    }
//
//    fun submitList(list: MutableList<Member>){
//        memberList = list
//        notifyDataSetChanged()
//    }
//
//    fun getItem(position: Int): Member {
//        return memberList[position]
//    }
//
//
//    inner class MemberViewHolder(private val binding: ItemMemberBinding) : RecyclerView.ViewHolder(binding.root){
//        init {
//            binding.root.setOnClickListener {
//                listener.onItemClick(adapterPosition)
//            }
//        }
//        fun bind(position: Int)= with(binding){
//            val item = memberList[position]
//            member = item
//        }
//    }
//}