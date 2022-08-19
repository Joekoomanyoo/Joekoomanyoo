package com.ssafy.heritage.view.profile

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.events.calendar.utils.EventsCalendarUtil.selectedDate
import com.events.calendar.views.EventsCalendar
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.ScheduleListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Schedule
import com.ssafy.heritage.databinding.FragmentMyCalendarBinding
import com.ssafy.heritage.databinding.ItemMyCalendarBinding
import com.ssafy.heritage.listener.ScheduleListClickListener
import com.ssafy.heritage.util.DividerItemDecoration
import com.ssafy.heritage.viewmodel.UserViewModel
import java.util.*

private const val TAG = "MyCalendarFragment___"

class MyCalendarFragment : BaseFragment<FragmentMyCalendarBinding>(R.layout.fragment_my_calendar),
    EventsCalendar.Callback {

    private val userViewModel by activityViewModels<UserViewModel>()

    private var cardBinding: ItemMyCalendarBinding? = null

    lateinit var callback: OnBackPressedCallback

    override fun init() {

        setCalendar()

        initObserver()

        initClickListener()

        setTextChangedListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로가기 눌렀을 때 원하는 작업 실행
                with(binding) {
                    // editText 포커스 되어있다면 뺏기
                    if (tilSchedule.editText!!.isFocused) {
                        tilSchedule.editText!!.clearFocus()
                    }
                    // 모션레이아웃 작동했다면 되돌리기
                    else if (motionlayout.progress > 0.0F) {
                        motionlayout.transitionToStart()
                    }
                    // 아무것도 아니라면 뒤로가기
                    else {
                        findNavController().popBackStack()
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    private fun initObserver() {
        userViewModel.myScheduleList.observe(viewLifecycleOwner) {
            binding.calendar.clearEvents()

            setCalendar()

            // 새로운 일정 등록시 목록 refresh
            if (cardBinding != null) {
                updateScheduleList()
            }
        }
    }

    private fun setCalendar() {
        val today = Calendar.getInstance()
        val end = Calendar.getInstance()
        end.add(Calendar.YEAR, 20)
        val start = Calendar.getInstance()
        start.add(Calendar.YEAR, -10)
        binding.calendar.setSelectionMode(binding.calendar.SINGLE_SELECTION)
            .setToday(today)
            .setMonthRange(start, end)
            .setWeekStartDay(Calendar.SUNDAY, false)
            .setIsBoldTextOnSelectionEnabled(true)
            .setCallback(this)
            .build()

        updateEvent()
    }

    fun updateEvent() {
        val it = userViewModel.myScheduleList.value ?: arrayListOf()
        it.forEach {
            val year = it.myScheduleDate.substring(0..3).toInt()
            val month = it.myScheduleDate.substring(4..5).toInt()
            val day = it.myScheduleDate.substring(6..7).toInt()
            Log.d(TAG, "initObserver: ${year} ${month} ${day}")
            val c: Calendar = Calendar.getInstance()
            c.set(year, month - 1, day)
            binding.calendar.addEvent(c)
        }
    }

    private fun initClickListener() = with(binding) {

        btnAddSchedule.setOnClickListener {
            if (!tilSchedule.editText?.text.isNullOrBlank()) {

                val schedule = Schedule(
                    userSeq = userViewModel.user.value?.userSeq!!,
                    myScheduleContent = tilSchedule.editText?.text.toString(),
                    myScheduleDate = getCurrentDate()
                )

                userViewModel.addSchedule(schedule)

                makeToast("일정등록이 완료되었습니다")
                tilSchedule.editText?.text?.clear()

            } else {
                makeTextInputLayoutError(tilSchedule, "일정을 입력해주세요")
            }
        }

        constraintBackground.setOnClickListener {
            if (!binding.etSchedule.isFocused) {
                binding.motionlayout.transitionToStart()
            }
        }
    }

    fun getCurrentDate(): String {
        binding.calendar.getCurrentSelectedDate()

        val year = "${selectedDate?.get(Calendar.YEAR)}"
        val month = if (selectedDate?.get(Calendar.MONTH)?.plus(1)!! < 10) {
            "0${selectedDate?.get(Calendar.MONTH)?.plus(1)}"
        } else {
            "${selectedDate?.get(Calendar.MONTH)?.plus(1)}"
        }
        val day = if (selectedDate?.get(Calendar.DAY_OF_MONTH)!! < 10) {
            "0${selectedDate?.get(Calendar.DAY_OF_MONTH)}"
        } else {
            "${selectedDate?.get(Calendar.DAY_OF_MONTH)}"
        }

        return "$year$month$day"
    }

    fun updateScheduleList() {
        val date = getCurrentDate()

        cardBinding!!.recyclerView.apply {
            val scheduleAdapter = ScheduleListAdapter()

            adapter = scheduleAdapter

            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            addItemDecoration(DividerItemDecoration(5F, resources.getColor(R.color.link_water)))

            // 선택한 날짜와 리스트에 있는 날짜가 같은 리스트만 뿌려줌
            val list = userViewModel.myScheduleList.value?.filter { it.myScheduleDate == date }
            scheduleAdapter.submitList(list)

            scheduleAdapter.scheduleListClickListener = object : ScheduleListClickListener {
                override fun onClick(position: Int, scheduleSeq: Int) {
                    userViewModel.deleteSchedule(scheduleSeq)
                    makeToast("일정이 삭제되었습니다")
                }
            }
        }
    }

    override fun onDayLongPressed(selectedDate: Calendar?) {
        Log.d(TAG, "onDayLongPressed: ")
    }

    override fun onDaySelected(selectedDate: Calendar?) {
        Log.d(TAG, "onDaySelected: $selectedDate")

        // 프레임에 있는 기존 뷰들 제거
        binding.frame.removeAllViews()

        // 날짜 정보 가져와서 파싱함
        binding.calendar.getCurrentSelectedDate()

        val year = "${selectedDate?.get(Calendar.YEAR)}"
        val month = if (selectedDate?.get(Calendar.MONTH)?.plus(1)!! < 10) {
            "0${selectedDate?.get(Calendar.MONTH)?.plus(1)}"
        } else {
            "${selectedDate?.get(Calendar.MONTH)?.plus(1)}"
        }
        val day = if (selectedDate?.get(Calendar.DAY_OF_MONTH)!! < 10) {
            "0${selectedDate?.get(Calendar.DAY_OF_MONTH)}"
        } else {
            "${selectedDate?.get(Calendar.DAY_OF_MONTH)}"
        }

        // 프레임 제목에 필요한 날짜 데이터
        val dateString = "${month}월 ${day}일"

        val inflater = LayoutInflater.from(context)
        cardBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_my_calendar, binding.frame, true)

        cardBinding!!.date = dateString

        // 일정 리스트 뿌리기
        updateScheduleList()

        // 프레임 뒤에 클릭 되는걸 방지
        cardBinding!!.root.setOnClickListener { Log.d(TAG, "onDaySelected: cardBinding") }

        binding.motionlayout.transitionToEnd()
    }

    override fun onMonthChanged(monthStartDate: Calendar?) {
        Log.d(TAG, "onMonthChanged: ")
    }

    private fun setTextChangedListener() = with(binding) {

        // 일정 입력창 에러 비활성화
        tilSchedule.editText?.addTextChangedListener {
            tilSchedule.isErrorEnabled = false
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    fun makeTextInputLayoutError(textInputLayout: TextInputLayout, msg: String) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = true
    }
}