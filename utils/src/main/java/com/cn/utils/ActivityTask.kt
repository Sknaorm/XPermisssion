package com.cn.utils

import android.app.Activity


class ActivityTask private constructor() {
    companion object {
        val instance by lazy { ActivityTask() }
    }

    private val mTask = arrayListOf<Activity?>()

    fun addToTask(activity: Activity?) {
        activity ?: return
        if (mTask.contains(activity)) {
            return
        }
        mTask.add(activity)
    }

    fun removeFromTask(activity: Activity?) {
        activity ?: return
        if (mTask.contains(activity)) {
            mTask.remove(activity)
        }
    }

    fun find(simpleName: String): Activity? {
        return mTask.find { it?.run { this::class.java.simpleName ?: "" == simpleName } ?: false }
    }

    fun isContains(activity: Activity?): Boolean {
        activity ?: return false
        return mTask.find { it == activity } != null
    }

    fun finish(activity: Activity?) {
        activity ?: return
        mTask.find { it == activity }?.apply {
            this.finish()
            if (mTask.contains(this)) {
                mTask.remove(this)
            }
        }
    }

    fun finishElse(activity: Activity) {
        mTask.filter { it != activity }.forEach {
            it?.finish()
            if (mTask.contains(it)) {
                mTask.remove(it)
            }
        }
    }

    fun finishAll() {
        mTask.forEach {
            it?.finish()
        }
    }
}