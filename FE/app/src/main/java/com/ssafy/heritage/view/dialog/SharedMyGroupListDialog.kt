package com.ssafy.heritage.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.adpter.MyGroupListAdapter
import com.ssafy.heritage.adpter.SharedMyGroupListAdapter
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import com.ssafy.heritage.databinding.DialogSharedMyGroupListBinding
import com.ssafy.heritage.databinding.ItemSharedMyGroupBinding
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.HeritageViewModel

private const val TAG = "SharedMyGroupListDialog___"

class SharedMyGroupListDialog(heritageSeq: Int) : DialogFragment() {

    var binding: DialogSharedMyGroupListBinding? = null
    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val heritageViewModel by activityViewModels<HeritageViewModel>()
    private lateinit var lastGroupListAdapter: MyGroupListAdapter
    private lateinit var sharedMyGroupListAdapter: SharedMyGroupListAdapter
    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()
    private var groupSeq = 0
    private var groupName = ""
    private val heritageSeq: Int = heritageSeq
    var selectPos = -1


    // 아이템 뿌려주는 거는 리뷰 리스트 참고하셈
    private fun initAdapter() {
        sharedMyGroupListAdapter = SharedMyGroupListAdapter()
        binding?.recyclerviewMyGroupList?.adapter = sharedMyGroupListAdapter
    }

    private fun initOpserver() {
        groupViewModel.myGroupList.observe(viewLifecycleOwner) {
            val list = it.filter { it.memberStatus != 0 && it.groupStatus != 'F' }
            sharedMyGroupListAdapter.submitList(list as MutableList<MyGroupResponse>)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSharedMyGroupListBinding.inflate(inflater, container, false)

        // 다이얼로그 뒤에 배경 흐리게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setContentView(R.layout.dialog_shared_my_group_list)

        // 내 모임 목록
        groupViewModel.selectMyGroups()

        initAdapter()
        initOpserver()

        // 가입한 모임이 없으면 "모임에 가입해보세요!" 뭐 이런 멘트 쳐야할 듯

        // 내 모임 아이템 클릭 -> groupSeq 저장
        // 작동 방식: 어댑터의 itemClickListener <-> OnItemClickListener <-> 프래그먼트의 setItemClickListener
        sharedMyGroupListAdapter.setOnItemClickListener(object :
        SharedMyGroupListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, data: MyGroupResponse, pos: Int) {
                groupSeq = data.groupSeq
                groupName = data.groupName
                groupViewModel.insertGroupDestination(groupSeq, heritageSeq, groupName, heritageViewModel.heritage.value?.heritageName!!)
                dismiss()

//                makeToast("${groupName}에 ${heritageViewModel.heritage.value!!.heritageName}이(가) 추가되었습니다.")
//                ((view.parent as RecyclerView).children).forEachIndexed { index, child ->
//                    if (index != pos) {
//                        // 시도 1
//                        // (view as ItemSharedMyGroupBinding).imagebtnCheck.visibility = View.GONE
//                        // 시도 2
//                        // ((view.parent as RecyclerView).findViewHolderForAdapterPosition(pos)
//                                as ItemSharedMyGroupBinding).imagebtnCheck.visibility = View.GONE
//                        Log.d(TAG, "onItemClick: ${((view.parent as RecyclerView).findViewHolderForAdapterPosition(pos))?.itemViewType}")
//                    }
//                }
            }
        })

//        // 공유하기 클릭 -> 모임 목적지로 현재 문화유산 추가
//        binding!!.btnSharing.setOnClickListener {
//            // 선택한 모임이 있다면
//            if (groupSeq != 0) {
//                groupViewModel.insertGroupDestination(groupSeq, heritageSeq, groupName, heritageViewModel.heritage.value?.heritageName!!)
//                makeToast("${groupName}에 ${heritageViewModel.heritage.value!!.heritageName}이(가) 추가되었습니다.")
//                dismiss()
//            } else {
//                makeToast("모임을 선택해주세요!")
//            }
//            // 모임 목적지에 현재 문화유산 추가(path: groupSeq)
////            groupViewModel.insertGroupDestination(groupSeq = )
//            // toast 띄우기
//        }

        // 돌아가기 클릭 -> 다이얼로그 닫힘
        binding!!.tvCloseDialog.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}