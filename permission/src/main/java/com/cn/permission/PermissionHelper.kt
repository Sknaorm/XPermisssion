package com.cn.permission

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import kotlin.properties.Delegates


class PermissionHelper private constructor(private val manager: FragmentManager, builder: Builder) {
    companion object {
        fun create(
            fragmentActivity: FragmentActivity,
            block: Builder.() -> Unit
        ): PermissionHelper {
            return create(fragmentActivity.supportFragmentManager, block)
        }

        fun create(fragment: Fragment, block: Builder.() -> Unit): PermissionHelper {
            return create(fragment.childFragmentManager, block)
        }

        fun create(manager: FragmentManager, block: Builder.() -> Unit): PermissionHelper {
            return PermissionHelper(manager, Builder().apply(block))
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

class Builder {
    internal var requestCode = (Math.random() * 100).toInt()
    internal val permissions: ArrayList<String> = arrayListOf()
    fun addPermission(name: String) {
        permissions.add(name)
    }
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
