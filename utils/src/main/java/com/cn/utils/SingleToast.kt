package com.cn.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.cn.utils.expand.getIndexOrNull

class SingleToast private constructor(val context: Context) {
    companion object {
        lateinit var instance: SingleToast
        fun init(context: Context) {
            instance = SingleToast(context.applicationContext)
        }
    }

    private var mLastText = "null&&null"
    private val mHandle = Handler(Looper.getMainLooper())
    private val mToast: Toast by lazy { Toast.makeText(context, "", Toast.LENGTH_SHORT) }
    private var isStartTask = false
    private val mTask = arrayListOf<Build>()
    fun show(str: String) {
        show { text = str }
    }

    fun show(block: Build.() -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showTask(block)
        } else {
            mHandle.post {
                showTask(block)
            }
        }
    }

    private fun showTask(block: (Build) -> Unit) {
        val build = Build().apply(block)
        if (build.mText == mLastText) {
            return
        } else {
            mLastText = checkNotNull(build.mText)
        }
        if (build.mCover) {
            mTask.clear()
            mToast.cancel()
            isStartTask = false
        }
        mTask.add(build)
        if (isStartTask) return
        showOneTask()
        isStartTask = true
    }

    private fun showOneTask() {
        val build = mTask.getIndexOrNull(0)
        if (build == null) {
            isStartTask = false
            return
        }
        mTask.removeAt(0)
        showInMainThread(build) {
            showOneTask()
        }
    }


    private fun showInMainThread(build: Build, block: () -> Unit) {
        val mText = build.mText ?: return
        val mDuration = build.mDuration
        val mGravity = build.mGravity
        val mXOffset = build.mXOffset
        val mYOffset = build.mYOffset
        val toast = mToast
        toast.setText(mText)
        mGravity?.apply { toast.setGravity(this, mXOffset, mYOffset) }
        val postDelayed = if (mDuration == Toast.LENGTH_LONG) 3500L else 2000L
        mHandle.postDelayed({
            block()
        }, postDelayed)
        mHandle.postDelayed({
            toast.show()
        }, 50)

    }
}
    class Build {
        internal var mText: String? = null
        internal var mDuration = Toast.LENGTH_SHORT
        internal var mGravity: Int? = null
        internal var mXOffset: Int = 0
        internal var mYOffset: Int = 0
        internal var mCover: Boolean = true
        var text: String? = null
            set(value) {
                value ?: return
                mText = value
                field = value
            }
        var duration: Int? = null
            set(value) {
                value ?: return
                mDuration = value
                field = value
            }
        var gravity: Int? = null
            set(value) {
                value ?: return
                mGravity = value
                field = value
            }
        var xOffset: Int? = null
            set(value) {
                value ?: return
                mXOffset = value
                field = value
            }
        var yOffset: Int? = null
            set(value) {
                value ?: return
                mYOffset = value
                field = value
            }
        var isCover: Boolean? = null
            set(value) {
                value ?: return
                mCover = value
                field = value
            }
    }
