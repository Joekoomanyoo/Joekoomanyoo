package com.ssafy.heritage.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import com.ssafy.heritage.databinding.ItemSharedMyGroupBinding
import com.ssafy.heritage.util.DateFormatter.formatShareDate

class SharedMyGroupListAdapter() :
    RecyclerView.Adapter<SharedMyGroupListAdapter.SharedMyGroupListViewHolder>() {

    private var myGroupList = mutableListOf<MyGroupResponse>()

    // 아이템 클릭 리스너 인터페이스
    interface OnItemClickListener {
        fun onItemClick(view: View, data: MyGroupResponse, pos: Int)
    }

    // 외부에서 클릭 시 이벤트 설정
    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    // 아이템 레이아웃과 결합
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SharedMyGroupListAdapter.SharedMyGroupListViewHolder {
        val binding =
            ItemSharedMyGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SharedMyGroupListViewHolder(binding)
    }

    // View에 내용 입력
    override fun onBindViewHolder(holder: SharedMyGroupListViewHolder, position: Int) {
        holder.bind(position)
    }

    // 아이템 개수
    override fun getItemCount(): Int {
        return myGroupList.size
    }

    fun submitList(list: MutableList<MyGroupResponse>) {
        myGroupList = list
        notifyDataSetChanged()
    }

    fun getItem(position: Int): MyGroupResponse {
        return myGroupList[position]
    }

    // 레이아웃 내 View 연결
    inner class SharedMyGroupListViewHolder(private val binding: ItemSharedMyGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) = with(binding) {
            val item = myGroupList[position]
//                ivGroupImg.setImageDrawable(ContextCompat.getDrawable(context, item.groupImgUrl)
            Glide.with(itemView.getContext()).load(item.groupImgUrl).into(ivGroupImg)
            tvGroupStartDate.text = "시작일: " + item.groupStartDate.formatShareDate()
//                tvGroupEndDate.text = item.groupEndDate.toString()
            tvGroupName.text = item.groupName

            // 아이템 클릭 시 onClick() 호출
            val pos = absoluteAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, item, pos)
                    imagebtnCheck.visibility = View.VISIBLE
                }
            }
        }
    }
}
