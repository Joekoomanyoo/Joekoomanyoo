package com.ssafy.heritage.listener

import com.ssafy.heritage.data.dto.StampCategory

interface CategoryListClickListener {
    fun onClick(position: Int, stampCategory: StampCategory)
}