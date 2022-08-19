package com.ssafy.heritage.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.ssafy.heritage.R
import com.ssafy.heritage.databinding.DialogArCheckBinding

private const val TAG = "ARCheckDialog___"
class ARCheckDialog(context: Context, arCheckDialogInterface: ARCheckDialogInterface) : Dialog(context) {

    private lateinit var binding :DialogArCheckBinding
    private val arCheckDialogInterface: ARCheckDialogInterface
        = arCheckDialogInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_ar_check,
            null,
            false
        )
        setContentView(binding.root)
        Log.d(TAG, "다이얼로그")
        // 배경 투명하게 바꿔줌
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initClickListener()
    }

    private fun initClickListener() = with(binding) {
        btnHome.setOnClickListener {
            arCheckDialogInterface.onHomeBtnClicked()
            dismiss()
        }
//        btnArList.setOnClickListener {
//            arCheckDialogInterface.onARListBtnClicked()
//            dismiss()
//        }
    }
}