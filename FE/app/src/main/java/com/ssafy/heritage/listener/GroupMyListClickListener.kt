package com.ssafy.heritage.listener

import com.ssafy.heritage.data.remote.response.MyGroupResponse

interface GroupMyListClickListener {
    fun onClick(position: Int, group: MyGroupResponse)
}