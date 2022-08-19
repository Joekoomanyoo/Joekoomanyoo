package com.ssafy.heritage.listener

import com.ssafy.heritage.data.dto.StampCategory
import com.ssafy.heritage.data.remote.response.MyGroupResponse

interface EvaluationClickListener {
    fun onClickEvaluationBtn(position: Int, myGroupResponse: MyGroupResponse)
}