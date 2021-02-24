package com.cn.permission

class PermissionResult(val data: List<PermissionData>) {
    val isGranted by lazy {
        var isGranted = true
        data.forEach {
            if (!it.isGranted) {
                isGranted = false
                return@forEach
            }
        }
        isGranted
    }
    val isNeverRefuse by lazy {
        data.find { it.isNeverRefuse } != null
    }
}