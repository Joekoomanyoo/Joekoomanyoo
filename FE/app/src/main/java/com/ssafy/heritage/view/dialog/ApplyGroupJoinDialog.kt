package com.ssafy.heritage.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ssafy.heritage.R
import com.ssafy.heritage.databinding.DialogApplyGroupJoinBinding

class ApplyGroupJoinDialog(context: Context, applyGroupJoinDialogInterface: ApplyGroupJoinDialogInterface) : Dialog(context) {

    private lateinit var binding :DialogApplyGroupJoinBinding
    private val applyGroupJoinDialogInterface: ApplyGroupJoinDialogInterface =
        applyGroupJoinDialogInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_apply_group_join,
            null,
            false
        )
        setContentView(binding.root)

        // 배경 투명하게 바꿔줌
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initClickListener()
    }

    private fun initClickListener() = with(binding) {
        btnOk.setOnClickListener {
            if (etAppeal.text.isNotBlank()) {
                applyGroupJoinDialogInterface.onOkBtnClicked(etAppeal.text.toString())
                dismiss()

            } else {
                Toast.makeText(context, "텍스트를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        btnCancel.setOnClickListener {
            dismiss()
        }
    }

}