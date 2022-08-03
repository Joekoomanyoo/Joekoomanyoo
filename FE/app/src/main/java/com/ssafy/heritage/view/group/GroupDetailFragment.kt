package com.ssafy.heritage.view.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.MemberAdapter
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.databinding.FragmentGroupDetailBinding
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = " GroupDetailFragment___"
class GroupDetailFragment : BaseFragment<FragmentGroupDetailBinding>(R.layout.fragment_group_detail),
    OnItemClickListener {

    private val args by navArgs<GroupDetailFragmentArgs>()

    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val user = User(0,0,"ssafy@naver.com", "잠만보", "1", "970317", "N", 'W', "","","","","",'N')
    private lateinit var memberAdapter:MemberAdapter

    override fun init() {
        groupViewModel.selectGroupMembers(args.groupInfo.groupSeq)
        initAdapter()
        initObserver()
        initView()

    }
    private fun initAdapter(){
        memberAdapter = MemberAdapter(this)
        binding.recyclerviewMembers.adapter = memberAdapter
    }
    private fun initObserver() = with(binding) {
        groupViewModel.groupMemberList.observe(viewLifecycleOwner){
            memberAdapter.submitList(it)
        }
        groupViewModel.detailInfo.observe(viewLifecycleOwner){
            Log.d(TAG, it.groupMakerNickname)
            // 현재유저가 방장이면
            if(it.groupMakerNickname == user.userNickname){
                Log.d(TAG, "USER IS GROUPMAKER")
                btnSubscription.visibility = View.GONE
                btnCancellation.visibility = View.GONE
                btnDrop.visibility = View.GONE
                if(it.groupStatus!='R'){
                    Log.d(TAG, "GROUP IS NOT RECRUTING")
                    headerApplicant.visibility = View.GONE
                    recyclerviewApplicant.visibility = View.GONE
                }
            }else{
                // 설정, 사진 변경버튼 제거
                Log.d(TAG, "USER IS NOT GROUPMAKER")
                Log.d(TAG, it.toString())
                btnSetting.visibility = View.GONE
                btnChangeImage.visibility =View.GONE
            }

            // 현재유저가 방장이면
            // 그룹상태가 모집중일시 신청자, 구성원이보여야함,
            // 그룹이 시작된 상태면 구성원만

            // 현재유저가 구성원이면,
            // 구성원, 탈퇴하기 버튼

            // 현재유저가 일반인이면,0
            // 구성원,
            // 가입하기 click => 가입 취소버튼
        }
    }

    private fun initView() = with(binding){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_detail, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GroupDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}