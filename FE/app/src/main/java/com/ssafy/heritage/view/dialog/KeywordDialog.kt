package com.ssafy.heritage.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ssafy.heritage.data.dto.Keyword
import com.ssafy.heritage.databinding.DialogKeywordBinding
import com.ssafy.heritage.viewmodel.UserViewModel

private const val TAG = "KeywordDialog___"

class KeywordDialog : DialogFragment() {

    var binding: DialogKeywordBinding? = null
    private val userViewModel by activityViewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogKeywordBinding.inflate(inflater, container, false)

        // 다이얼로그 뒤에 배경 흐리게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.btnSave.setOnClickListener {

            // 값이 읿력되었는지 확인
            if (!binding!!.name.isNullOrBlank()) {
                val keyword = Keyword(
                    myKeywordSeq = 0,
                    userSeq = userViewModel.user.value?.userSeq!!,
                    binding!!.name!!,
                    ""
                )
                userViewModel.insertKeyword(keyword)
                dismiss()
            } else {
                makeToast("값을 입력해주세요")
            }
        }

        binding!!.btnCancel.setOnClickListener {
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