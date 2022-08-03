package com.ssafy.heritage.view.group

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.GroupAttribute
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.FragmentGroupModifyBinding
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = "GroupModifyFragment___"

class GroupModifyFragment :
    BaseFragment<FragmentGroupModifyBinding>(R.layout.fragment_group_modify) {

    private val groupViewModel by viewModels<GroupViewModel>()
    private lateinit var groupInfo: GroupListResponse
    private var region: String = ""
    private var age: Int = 0
    private var max: Int = 0
    private var child: Char = 'N'
    private var global: Char = 'N'
    private var name: String = ""
    private var content: String = ""
    private var startDate:Int = 0
    private var endDate:Int = 0
    private var groupAccessType:Char = 'N'
    private var groupPwd: String=""

    override fun init() {

        initObserver()

        initClickListener()

        setItemSelectedListener()
    }

    private fun initObserver() {
        groupViewModel.insertGroupInfo.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(context, "모임이 등록되었습니다.", Toast.LENGTH_SHORT)
                    .show() // 추후에 모임세부화면으로 이동하겠냐는 다이얼로그 추가
                val action =
                    GroupModifyFragmentDirections.actionGroupModifyFragmentToGroupInfoFragment(it)
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "모임이 등록되지 않았습니다. 서버오류", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initClickListener() = with(binding) {

        // 아이 동반 여부
        cbKids.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                child = 'Y'
            } else {
                child = 'N'
            }
        }

        // 글로벌 모임 여부
        cbGlobal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                global = 'Y'
            } else {
                global = 'N'
            }
        }

        // 모임 만들기 버튼 클릭 시
        btnCreateGroup.setOnClickListener {
            //groupInfo = GroupListResponse(0,0,"",0,"",'N',"",'Y', 'R',2, groupAttribute = )
            name = etGroupName.text.toString()
            content = etGroupContent.text.toString()
            startDate = etGroupStartDate.text.toString().toInt()
            endDate = etGroupEndDate.text.toString().toInt()

            when {
                name == "" -> {
                    Toast.makeText(context, "모임 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                content == "" -> {
                    Toast.makeText(context, "모임 설명을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                startDate == 0 -> {
                    Toast.makeText(context, "모임 시작일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                endDate == 0 -> {
                    Toast.makeText(context, "모임 종료일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            if (name != "" && content != "" && startDate != 0 && endDate != 0) {
                if (etGroupPassword.text.toString() != "") {
                    // 공개
                    groupAccessType = '0'
                } else {
                    // 비공개
                    groupAccessType = '1'
                    groupPwd = etGroupPassword.text.toString()
                }
                // groupMaker : 현재 유저 번호로 넣어야함

                groupInfo = GroupListResponse("잠만보", name, content,groupAccessType,groupPwd,'Y', 'R',max,
                    region,startDate,endDate,age,child,global,0)
                groupViewModel.insertGroup(groupInfo)
            }
            //if ()
        }

        btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    // 스피너 선택 아이템 선택 시
    private fun setItemSelectedListener() = with(binding) {

        // 최대인원 설정
        spinnerMaximum.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            Log.d(TAG, "setItemSelectedListener: $newItem")
            max = newItem.toInt()
        }

        // 나이대 선택
        spinnerAge.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            Log.d(TAG, "setItemSelectedListener: $newItem")
            age = newItem.toInt()
        }

        // 지역 선택
        spinnerRegion.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            Log.d(TAG, "setItemSelectedListener: $newItem")
            region = newItem
        }
    }

}