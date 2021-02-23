package com.cn.permission

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import kotlin.properties.Delegates


class PermissionHelper internal constructor(
    private val manager: FragmentManager,
    builder: Builder
) {
    companion object {
        fun newBuild(fragmentActivity: FragmentActivity): Builder {
            return Builder(fragmentActivity.supportFragmentManager)
        }

        fun newBuild(fragment: Fragment): Builder {
            return Builder(fragment.childFragmentManager)
        }

        fun newBuild(manager: FragmentManager): Builder {
            return Builder(manager)
        }
    }

    private val mRequestFragment by lazy { PermissionFragment.getInstance(manager) }
    internal var requestCode = builder.requestCode
    internal val permissions = builder.permissions
    fun requestResult(block: (Result) -> Unit) {
        mRequestFragment.request(requestCode, permissions) {
            block(Result(it))
        }
    }

    fun request(block: (Boolean) -> Unit) {
        requestResult {
            block(it.isGranted)
        }
    }

    fun checkDeniedByLastRequest(block: (List<String>) -> Unit) {
        val checkPermission = mRequestFragment.checkDeniedByLastRequest(permissions)
        block(checkPermission)
    }
}

class Builder(internal val fragmentManager: FragmentManager) {
    internal var requestCode = (Math.random() * 100).toInt()
    internal val permissions: ArrayList<String> = arrayListOf()
    fun addPermission(name: String) = apply {
        permissions.add(name)
    }

    fun build() = PermissionHelper(fragmentManager, this)
}

class Result(val data: List<PermissionData>) {
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

data class PermissionData(val name: String, val isGranted: Boolean, val isNeverRefuse: Boolean)
