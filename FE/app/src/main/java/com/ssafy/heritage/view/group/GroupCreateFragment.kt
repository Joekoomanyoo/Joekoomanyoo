package com.ssafy.heritage.view.group

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.request.GroupAddRequest
import com.ssafy.heritage.databinding.FragmentGroupCreateBinding
import com.ssafy.heritage.viewmodel.GroupViewModel
import java.util.*


private const val TAG = "GroupCreateFragment___"

class GroupCreateFragment :
    BaseFragment<FragmentGroupCreateBinding>(R.layout.fragment_group_create) {
    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()
    val userNickname: String = ApplicationClass.sharedPreferencesUtil.getUserNickName()!!
    private val groupViewModel by viewModels<GroupViewModel>()
    private lateinit var groupInfo: GroupAddRequest
    private var region: String = ""
    private var age: Int = 0
    private var max: Int = 0
    private var child: Char = 'N'
    private var global: Char = 'N'
    private var name: String = ""
    private var content: String = ""
    private var startDate:String = ""
    private var endDate:String = ""
    private var groupAccessType:Char = 'N'
    private var groupPwd: String=""

    @RequiresApi(Build.VERSION_CODES.O)
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
                val action = GroupCreateFragmentDirections.actionGroupCreateFragmentToGroupInfoFragment(it)
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "모임이 등록되지 않았습니다. 서버오류", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initClickListener() = with(binding) {

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        // 시작일
        etGroupStartDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                val m = mmonth +1
                etGroupStartDate.setText(""+  myear+"-"+ m +"-"+mdayOfMonth )
            }, year, month, day)
            datePickerDialog.show()
        }

        // 종료일
        etGroupEndDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                val m = mmonth +1
                etGroupEndDate.setText(""+  myear+"-"+ m +"-"+mdayOfMonth )
            }, year, month, day)
            datePickerDialog.show()
        }

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
            name = etGroupName.text.toString()
            content = etGroupContent.text.toString()
            startDate = etGroupStartDate.text.toString()
            endDate = etGroupEndDate.text.toString()

            when {
                name == "" -> {
                    Toast.makeText(context, "모임 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                content == "" -> {
                    Toast.makeText(context, "모임 설명을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                startDate == "" -> {
                    Toast.makeText(context, "모임 시작일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                endDate == "" -> {
                    Toast.makeText(context, "모임 종료일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            if (name != "" && content != "" && startDate != "" && endDate != "") {
                if (etGroupPassword.text.toString() != "") {
                    // 공개
                    groupAccessType = '0'
                } else {
                    // 비공개
                    groupAccessType = '1'
                    groupPwd = etGroupPassword.text.toString()
                }
                // 현재시간을 가져오기
                val long_now = System.currentTimeMillis()
                // 현재 시간을 Date 타입으로 변환
                val t_date = Date(long_now)
                groupInfo = GroupAddRequest( name,"", content, 'Y',groupPwd, max,
                    region, startDate,
                    endDate, age, child, global,'Y', 'R')
                groupViewModel.insertGroup(groupInfo)
            }
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