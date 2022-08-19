package com.ssafy.heritage.listener

import android.view.View
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.data.dto.Member


interface MemberClickListener {
    fun onClick(position: Int, member: Member, view: View)
}