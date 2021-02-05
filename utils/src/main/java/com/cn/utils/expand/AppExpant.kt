package com.cn.utils.expand

import android.content.Context
import android.content.res.Resources

fun Any.getStatusBarHeight(): Int {
    val a = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
    return if (a > 0)
        Resources.getSystem().getDimensionPixelSize(a)
    else
        0
}

fun Any.getToolbarHeight(context: Context): Int {
    val actionbarSizeTypedArray =
        context.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
    val h = actionbarSizeTypedArray.getDimension(0, 0f)
    actionbarSizeTypedArray.recycle()
    return h.toInt()
}