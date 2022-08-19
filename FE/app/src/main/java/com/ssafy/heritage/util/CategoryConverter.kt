package com.ssafy.heritage.util

import com.ssafy.heritage.R

object CategoryConverter {
    val categoryMap = mutableMapOf(
        "전체" to 0,
        "탑" to 1,
        "비" to 2,
        "불교" to 3,
        "공예품" to 4,
        "궁궐" to 5,
        "기록유산" to 6,
        "왕릉" to 7,
        "건축" to 8,
        "종" to 9,
        "기타" to 10,
    )

    val imageMap = mutableMapOf(
        "탑" to R.drawable.category_1,
        "비" to R.drawable.category_2,
        "불교" to R.drawable.category_3,
        "공예품" to R.drawable.category_4,
        "궁궐" to R.drawable.category_5,
        "기록유산" to R.drawable.category_6,
        "왕릉" to R.drawable.category_7,
        "건축" to R.drawable.category_8,
        "종" to R.drawable.category_9,
        "기타" to R.drawable.category_10
    )

    val chipMap = mutableMapOf(
        "1" to R.id.chip_1,
        "2" to R.id.chip_2,
        "3" to R.id.chip_3,
        "4" to R.id.chip_4,
        "5" to R.id.chip_5,
        "6" to R.id.chip_6,
        "7" to R.id.chip_7,
        "8" to R.id.chip_8,
        "9" to R.id.chip_9,
        "10" to R.id.chip_10
    )
}