package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.data.remote.response.StampRankResponse
import com.ssafy.heritage.databinding.ItemRankBinding

class StampRankAdapter() :
    RecyclerView.Adapter<StampRankAdapter.StampListViewHolder>() {

    private var stampRankList = mutableListOf<StampRankResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampListViewHolder {
        val binding = ItemRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StampListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StampListViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return stampRankList.size
    }

    fun submitList(list: MutableList<StampRankResponse>) {
        stampRankList = list
        notifyDataSetChanged()
    }

    fun getItem(position: Int): StampRankResponse {
        return stampRankList[position]
    }

    inner class StampListViewHolder(private val binding: ItemRankBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) = with(binding) {
            val item = stampRankList[position]
            rankInfo = item
        }
    }
}