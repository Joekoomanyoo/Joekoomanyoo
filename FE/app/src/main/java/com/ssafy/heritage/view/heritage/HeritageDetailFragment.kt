package com.ssafy.heritage.view.heritage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentHeritageDetailBinding

private const val TAG = "HeritageDetailFragment___"

private const val ARG_HERITAGE = "heritage"

class HeritageDetailFragment :
    BaseFragment<FragmentHeritageDetailBinding>(R.layout.fragment_heritage_detail) {

    private var heritage: Heritage? = null

    @SuppressLint("LongLogTag")
    override fun init() {

        arguments?.let {
            heritage = it.getSerializable(ARG_HERITAGE) as Heritage
            Log.d(TAG, "init: $heritage")
        }
    }

    companion object {
        fun newInstance(param: Heritage) =
            HeritageDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_HERITAGE, param)
                }
            }
    }
}