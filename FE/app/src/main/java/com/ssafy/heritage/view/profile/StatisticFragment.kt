package com.ssafy.heritage.view.profile

import android.graphics.Color
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Stamp
import com.ssafy.heritage.databinding.FragmentStatisticBinding
import com.ssafy.heritage.viewmodel.UserViewModel

private const val TAG = "StatisticFragment___"

class StatisticFragment : BaseFragment<FragmentStatisticBinding>(R.layout.fragment_statistic) {

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {

//        initPieChart()

        initObserver()

    }
//
//    private fun initPieChart() = with(binding) {
//        chart.apply {
//            setUsePercentValues(true)
//            getDescription().setEnabled(false)
//            setRotationEnabled(true)
//            setDragDecelerationFrictionCoef(0.9f)
//            setRotationAngle(0f)
//            setHighlightPerTapEnabled(true)
//            animateY(1400, Easing.EasingOption.EaseInOutQuad)
//            setHoleColor(Color.parseColor("#000000"))
//        }
//    }

    private fun initObserver() {
        userViewModel.myStampList.observe(viewLifecycleOwner) {
            showChart(it)
        }
    }

    private fun showChart(data: MutableList<Stamp>) {
        val map = data.groupBy { it.stampCategory }
        val typeAmountMap = HashMap<String, Int>()

        map.forEach {
            val key = it.key
            val num = it.value.size
            typeAmountMap.put(key, num)
        }

        val pieEntries = arrayListOf<PieEntry>()
        Log.d(TAG, "initChart typeAmountMap: $typeAmountMap")

        val colorList = arrayListOf<Int>(
            Color.parseColor("#000000"),
            Color.parseColor("#FF0000"),
            Color.parseColor("#00FF00"),
            Color.parseColor("#0000FF"),
            Color.parseColor("#FFFFFF"),
        )

        typeAmountMap.keys.forEach {
            pieEntries.add(PieEntry(typeAmountMap.get(it)?.toFloat()!!, it))
        }

        Log.d(TAG, "initChart pieEntries: $pieEntries")

        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.valueTextSize = 12f
        pieDataSet.colors = colorList
        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(true)

        binding.chart.apply {
            setData(pieData)
            invalidate()
            getDescription().setEnabled(false)
            setUsePercentValues(true)
        }
    }
}