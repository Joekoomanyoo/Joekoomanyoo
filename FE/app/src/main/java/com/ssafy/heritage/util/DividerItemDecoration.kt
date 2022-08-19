package com.ssafy.heritage.util

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration : RecyclerView.ItemDecoration {

    private val mPaint: Paint = Paint()

    private var mHeight = 0f

    constructor(a_height: Float, a_color: Int) {
        mHeight = a_height
        mPaint.setColor(a_color)
    }

    override fun onDrawOver(a_canvas: Canvas, a_parent: RecyclerView, a_state: RecyclerView.State) {
        super.onDrawOver(a_canvas, a_parent, a_state)
        val left = a_parent.paddingLeft
        val right = a_parent.width - a_parent.paddingRight
        val childCount = a_parent.childCount
        for (i in 0 until childCount) {
            val child: View = a_parent.getChildAt(i)
            val params = child.getLayoutParams() as RecyclerView.LayoutParams
            val top: Int = child.getBottom() + params.bottomMargin
            val bottom = top + mHeight
            a_canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom, mPaint)
        }
    }
}