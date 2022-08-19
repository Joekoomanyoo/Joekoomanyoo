package com.ssafy.heritage.util

import java.text.SimpleDateFormat
import java.util.*

fun formatter(time: Date?): String {
    if(time == null){
        return ""
    }
    val dateFormat = SimpleDateFormat("yyyy.MM.dd")
    dateFormat.timeZone = TimeZone.getTimeZone("Seoul/Asia")

    return dateFormat.format(time)
}

fun formatterToDate(time:String):Date {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    dateFormat.timeZone = TimeZone.getTimeZone("Seoul/Asia")

    return dateFormat.parse(time)
}