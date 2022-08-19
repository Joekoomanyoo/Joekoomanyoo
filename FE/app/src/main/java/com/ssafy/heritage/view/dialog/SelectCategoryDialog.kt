package com.ssafy.heritage.view.dialog

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.ssafy.heritage.adpter.GroupListAdapter
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.DialogSelectCategoryBinding
import java.util.*

private const val TAG = "SelectCategoryDialog___"

class SelectCategoryDialog : DialogFragment() {

    var binding: DialogSelectCategoryBinding? = null

    private var region: String = ""
    private var age: Int = 0
    private var child: Char = 'N'
    private var global: Char = 'N'
    private var startDate: String = ""
    private var endDate: String = ""

    lateinit var adapter: GroupListAdapter
    lateinit var list: MutableList<GroupListResponse>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSelectCategoryBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListener()

        setItemSelectedListener()
    }

    private fun initClickListener() = with(binding!!) {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        // 시작일
        etGroupStartDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                    val m = mmonth + 1
                    val mm = if (m < 10) "0$m" else m
                    val day = if (mdayOfMonth < 10) "0$mdayOfMonth" else mdayOfMonth
                    etGroupStartDate.setText("" + myear + "-" + mm + "-" + day)
                }, year, month, day
            )
            datePickerDialog.show()
        }

        // 종료일
        etGroupEndDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                    val m = mmonth + 1
                    val mm = if (m < 10) "0$m" else m
                    val day = if (mdayOfMonth < 10) "0$mdayOfMonth" else mdayOfMonth
                    etGroupEndDate.setText("" + myear + "-" + mm + "-" + day)
                }, year, month, day
            )
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

        btnOk.setOnClickListener {
            Log.d(TAG, "initClickListener: $list")
            startDate = etGroupStartDate.text.toString()
            endDate = etGroupEndDate.text.toString()
            Log.d(TAG, "initClickListener startDate: $startDate")
            Log.d(TAG, "initClickListener endDate: $endDate")
            Log.d(TAG, "initClickListener region: $region")
            Log.d(TAG, "initClickListener age: $age")
            Log.d(TAG, "initClickListener child: $child")
            Log.d(TAG, "initClickListener global: $global")

            if (startDate != "") {
                list =
                    list.filter {
                        Log.d(TAG, "initClickListener: ${it.groupStartDate >= startDate}")

                        it.groupStartDate >= startDate
                    } as MutableList<GroupListResponse>
            }

            if (endDate != "") {
                list = list.filter { it.groupEndDate <= endDate } as MutableList<GroupListResponse>
            }
            if (region != "") {
                list = list.filter { it.groupRegion == region } as MutableList<GroupListResponse>
            }
            if (age != 0) {
                list = list.filter { it.groupAgeRange == age } as MutableList<GroupListResponse>
            }
            if (child == 'Y') {
                list = list.filter { it.groupWithChild == child } as MutableList<GroupListResponse>
            }
            if (global == 'Y') {
                list =
                    list.filter { it.groupWithGlobal == global } as MutableList<GroupListResponse>
            }

            adapter.submitList(list)

            Log.d(TAG, "initClickListener: $list")

            dismiss()
        }
    }

    // 스피너 선택 아이템 선택 시
    private fun setItemSelectedListener() = with(binding!!) {

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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}