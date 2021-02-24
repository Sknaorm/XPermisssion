package com.cn.permission

class Result(val data: List<Data>) {
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