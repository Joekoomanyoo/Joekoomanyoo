package com.ssafy.heritage.listener

import android.view.View
import com.ssafy.heritage.data.remote.response.FeedListResponse

interface FeedListClickListener {
    fun onClick(position: Int, feed: FeedListResponse, view: View)
}