package com.ssafy.heritage.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.ssafy.heritage.R
import com.ssafy.heritage.data.dto.Member
import com.ssafy.heritage.databinding.DialogOtherProfileBinding


private const val TAG = "OtherProfileDialog___"

class OtherProfileDialog(context: Context, member: Member, userPermission: Int, otherProfileDialogInterface: OtherProfileDialogInterface) : Dialog(context) {

    private lateinit var binding: DialogOtherProfileBinding
    private val otherProfileDialogInterface: OtherProfileDialogInterface =
        otherProfileDialogInterface

    private val member: Member = member
    private val userPermission: Int = userPermission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_other_profile, null, false)
        setContentView(binding.root)

        binding.member= member

        // 배경 투명하게 바꿔줌
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        Log.d(TAG, "PERMISSION: $userPermission")
        Log.d(TAG, "NAME: ${member.memberNickname}")

        // 현재 유저가 방장일 때
        if (userPermission == 2) {
            if (member.memberStatus == 0) {
                binding.btnDrop.visibility = View.GONE
                binding.btnRefuse.visibility = View.VISIBLE
                binding.btnApprove.visibility = View.VISIBLE
            }else if (member.memberStatus == 1) {
                binding.btnDrop.visibility = View.VISIBLE // 강제퇴장
                binding.btnRefuse.visibility = View.GONE
                binding.btnApprove.visibility = View.GONE
            }else{
                binding.btnDrop.visibility = View.GONE
                binding.btnRefuse.visibility = View.GONE
                binding.btnApprove.visibility = View.GONE
            }
        } else {
            binding.btnDrop.visibility = View.GONE
            binding.btnRefuse.visibility = View.GONE
            binding.btnApprove.visibility = View.GONE
        }

        initClickListener()

    }

    private fun initClickListener() = with(binding) {
        btnApprove.setOnClickListener {
            Log.d(TAG, member!!.userSeq.toString())
            otherProfileDialogInterface.onApproveBtnClicked(member!!.userSeq)
            dismiss()
        }
        btnRefuse.setOnClickListener {
            Log.d(TAG, "가입 거절 : ${member!!.userSeq}, ${member!!.memberNickname}")
            otherProfileDialogInterface.onRefuseBtnClicked(member!!.userSeq)
            dismiss()
        }
        btnDrop.setOnClickListener {
            Log.d(TAG, "강제 퇴장 : ${member!!.userSeq}, ${member!!.memberNickname}")
            otherProfileDialogInterface.onDropBtnClicked(member!!.userSeq)
            dismiss()
        }
        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}