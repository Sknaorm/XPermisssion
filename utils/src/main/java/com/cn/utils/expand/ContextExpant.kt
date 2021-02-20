package com.cn.utils.expand

import android.content.Context
import android.content.res.Resources
import android.os.Build

fun Context.getAppName(): String {
    try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val labelRes = packageInfo.applicationInfo.labelRes
        return resources.getString(labelRes)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

fun Context.getAppVersionCode(): Long {
    try {
        val packageManager = packageManager
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            packageInfo.versionCode.toLong()
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return 0
}

fun Context.getAppVersionName(): String {
    try {
        val packageManager = packageManager
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        return packageInfo.versionName
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return ""
}

fun Context.getStatusBarHeight(): Int {
    val system = Resources.getSystem()
    val a = system.getIdentifier("status_bar_height", "dimen", "android")
    return if (a > 0)
        system.getDimensionPixelSize(a)
    else
        0
}

fun Context.getToolbarHeight(): Int {
    val actionbarSizeTypedArray = obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
    val h = actionbarSizeTypedArray.getDimension(0, 0f)
    actionbarSizeTypedArray.recycle()
    return h.toInt()
}
