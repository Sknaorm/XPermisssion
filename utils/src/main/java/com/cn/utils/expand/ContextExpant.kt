package com.cn.utils.expand

import android.content.Context
import android.content.res.Resources
import android.os.Build

fun getAppName(context: Context): String {
    try {
        val packageManager = context.packageManager
        val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
        val labelRes = packageInfo.applicationInfo.labelRes
        return context.resources.getString(labelRes)
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

