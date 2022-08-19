package com.ssafy.heritage.view.group

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.MemberAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Member
import com.ssafy.heritage.data.remote.request.GroupJoin
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.FragmentGroupDetailBinding

import com.ssafy.heritage.listener.MemberClickListener
import com.ssafy.heritage.view.dialog.ApplyGroupJoinDialog
import com.ssafy.heritage.view.dialog.ApplyGroupJoinDialogInterface
import com.ssafy.heritage.view.dialog.OtherProfileDialog
import com.ssafy.heritage.view.dialog.OtherProfileDialogInterface
import com.ssafy.heritage.viewmodel.GroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = " GroupDetailFragment___"

class GroupDetailFragment :
    BaseFragment<FragmentGroupDetailBinding>(R.layout.fragment_group_detail),
    ApplyGroupJoinDialogInterface, OtherProfileDialogInterface {

    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()
    private val memberAdapter: MemberAdapter by lazy { MemberAdapter() }
    private val applicantAdapter: MemberAdapter by lazy { MemberAdapter() }
    private lateinit var groupInfo: GroupListResponse

    override fun init() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.groupVM = groupViewModel
            Log.d(TAG, "init: ${groupViewModel.groupPermission.value}")
            initAdapter()
            initObserver()
            initClickListener()
        }
    }

    private fun initAdapter() {
        binding.recyclerviewMembers.apply {
            adapter = memberAdapter
            memberAdapter.memberClickListener = object : MemberClickListener {
                override fun onClick(position: Int, member: Member, view: View) {
                    Log.d(TAG, "initAdapter : ${groupViewModel.groupPermission.value!!}")
                    Log.d(TAG, "initAdapter : ${member.toString()}")
                    val dialog = OtherProfileDialog(
                        requireContext(),
                        member,
                        userPermission = groupViewModel.groupPermission.value!!,
                        this@GroupDetailFragment
                    )
                    dialog.show()
                }
            }
        }
        binding.recyclerviewApplicant.apply {
            adapter = applicantAdapter
            applicantAdapter.memberClickListener = object : MemberClickListener {
                override fun onClick(position: Int, member: Member, view: View) {
                    Log.d(TAG, "initAdapter : ${groupViewModel.groupPermission.value!!}")
                    val dialog = OtherProfileDialog(
                        requireContext(),
                        member,
                        userPermission = groupViewModel.groupPermission.value!!,
                        this@GroupDetailFragment
                    )
                    dialog.show()
                }

            }
        }
    }

    private fun initObserver() = with(binding) {
        groupViewModel.groupPermission.observe(viewLifecycleOwner) {
            // 신청자 목록
//            headerApplicant.visibility = View.GONE
//            recyclerviewApplicant.visibility = View.GONE
        }
        groupViewModel.groupMemberList.observe(viewLifecycleOwner) {
            Log.d(TAG, "initObserver groupMemberList: $it")
            var applicantList = mutableListOf<Member>()
            var memberList = mutableListOf<Member>()
            for (i in it) {
                // 신청자일 경우
                if (i.memberStatus == 0) {
                    applicantList.add(i)
                }
                // 회원 또는 방장일 경우
                if (i.memberStatus == 1 || i.memberStatus == 2) {
                    memberList.add(i)
                }
            }
            applicantAdapter.submitList(applicantList)
            memberAdapter.submitList(memberList)
        }
        groupViewModel.detailInfo.observe(viewLifecycleOwner) {
            groupDetailInfo = it
            groupInfo = it
        }
    }

    private fun initClickListener() = with(binding) {

        // 설정
//        binding.btnSetting.setOnClickListener{
//            val action = GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupModifyFragment(groupInfo)
//            findNavController().navigate(action)
//        }
//
//        binding.btnChangeImage.setOnClickListener {
//
//        }

        // 가입 요청
        binding.btnSubscription.setOnClickListener {
            val dialog = ApplyGroupJoinDialog(requireContext(), this@GroupDetailFragment)
            dialog.show()
            it.visibility = View.GONE
            binding.btnCancellation.visibility = View.VISIBLE
        }

        // 가입취소
        binding.btnCancellation.setOnClickListener {
            // 모임을 떠나시겠습니까? 다이얼로그
            val dialog = SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("가입을 취소하실건가요?")
                .setConfirmText("네, 취소하겠습니다")
                .setCancelText("아니요, 안할래요!")
                .showCancelButton(true)
                .setCancelClickListener {
                    it.cancel()
                }
                .setConfirmClickListener {
                    groupViewModel.leaveGroupJoin(groupInfo.groupSeq)
                    Toast.makeText(requireContext(), "가입을 취소했습니다.", Toast.LENGTH_SHORT).show()
                    groupViewModel.setGroupPermission(3)
                    it.dismissWithAnimation()
                    binding.btnCancellation.visibility = View.GONE
                    binding.btnSubscription.visibility = View.VISIBLE
                }
                .show()
        }

        // 탈퇴하기
        binding.btnDrop.setOnClickListener {
            // 모임을 떠나시겠습니까? 다이얼로그
            SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("정말 떠나실건가요?")
                .setContentText("모임의 정보는 저장되지 않습니다. 그래도 탈퇴 하시겠습니까?")
                .setConfirmText("네, 탈퇴하겠습니다")
                .setCancelText("아니요, 안할래요!")
                .showCancelButton(true)
                .setCancelClickListener {
                    it.cancel()
                }
                .setConfirmClickListener {
                    groupViewModel.leaveGroupJoin(groupInfo.groupSeq)
                    Toast.makeText(requireActivity(), "모임을 탈퇴했습니다.", Toast.LENGTH_SHORT).show()
                    groupViewModel.setGroupPermission(3)
                    it.dismissWithAnimation()
                    findNavController().popBackStack()
                }
                .show()
        }

        // 모임시작
        btnGroupStart.setOnClickListener {
            val map = HashMap<String, String>()
            map.put("groupActive", "Y")
            map.put("groupStatus", "O")
            groupViewModel.setGroupStatus(map)

            makeToast("모임이 시작되었습니다")
            findNavController().popBackStack()
        }

        // 모임종료
        btnGroupStop.setOnClickListener {
            val map = HashMap<String, String>()
            map.put("groupActive", "N")
            map.put("groupStatus", "F")
            groupViewModel.setGroupStatus(map)

            makeToast("모임이 완료되었습니다")
            findNavController().popBackStack()
        }

        // 모임삭제
        btnGroupRemove.setOnClickListener {
            val map = HashMap<String, String>()
            map.put("groupActive", "N")
            map.put("groupStatus", "F")
            groupViewModel.setGroupStatus(map)

            makeToast("모임이 삭제되었습니다")
            findNavController().popBackStack()
        }
    }

    override fun onOkBtnClicked(appeal: String) {
        groupViewModel.applyGroupJoin(groupInfo.groupSeq, GroupJoin(appeal, userSeq))
        Log.d(TAG, "가입을 요청했습니다.")
        Toast.makeText(requireActivity(), "가입을 요청했습니다.", Toast.LENGTH_SHORT).show()
        groupViewModel.setGroupPermission(0)
        // 방장에게 알림 전송
    }

    override fun onApproveBtnClicked(userSeq: Int) {
        groupViewModel.approveGroupJoin(groupInfo.groupSeq, userSeq)
        Log.d(TAG, "가입을 승인했습니다")
        Toast.makeText(requireActivity(), "가입을 승인했습니다", Toast.LENGTH_SHORT).show()
        // 그 회원에게 알림 전송
    }

    override fun onRefuseBtnClicked(userSeq: Int) {
        groupViewModel.leaveGroupJoin(groupInfo.groupSeq)
        Log.d(TAG, "가입을 요청을 거절했습니다.")
        Toast.makeText(requireActivity(), "가입을 요청을 거절했습니다.", Toast.LENGTH_SHORT).show()
        // 그 회원에게 알림 전송
    }

    override fun onDropBtnClicked(userSeq: Int) {
        groupViewModel.outGroupJoin(groupInfo.groupSeq, userSeq)
        Toast.makeText(requireActivity(), "${userSeq}를 강제 퇴장시켰습니다", Toast.LENGTH_SHORT).show()
        // 그 회원에게 알림 전송
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}
