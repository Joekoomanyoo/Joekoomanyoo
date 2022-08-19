package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.remote.response.EvaluationProfileResponse
import com.ssafy.heritage.databinding.ItemEvaluationListBinding

class EvaluationMemberListAdapter(private val listener: OnItemClickListener):
    RecyclerView.Adapter<EvaluationMemberListAdapter.EvaluationMemberListViewHolder>(){

    var memberList = mutableListOf<EvaluationProfileResponse>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EvaluationMemberListAdapter.EvaluationMemberListViewHolder {
        val binding = ItemEvaluationListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EvaluationMemberListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: EvaluationMemberListAdapter.EvaluationMemberListViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return memberList.size
    }
    fun submitList(list: MutableList<EvaluationProfileResponse>) {
        memberList = list
        notifyDataSetChanged()
    }

    fun getItem(position: Int): EvaluationProfileResponse {
        return memberList[position]
    }

    inner class EvaluationMemberListViewHolder(private val binding: ItemEvaluationListBinding):
        RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                if(!binding.ivCheck.isVisible){
                    listener.onItemClick(adapterPosition)
                    binding.ivCheck.visibility = View.VISIBLE
                }
            }
        }
        fun bind(position: Int){
            val item = memberList[position]
            binding.profile = item
        }
    }
}