package com.ssafy.heritage.listener

import android.view.View
import com.ssafy.heritage.data.dto.Heritage

interface HeritageListClickListener {
    fun onClick(position: Int, heritage: Heritage, view: View)
}