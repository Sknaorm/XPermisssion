package com.cn.permission

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


class XPermission internal constructor(
    private val manager: FragmentManager,
    builder: Builder
) {
    companion object {
        fun newInstance(
            fragmentActivity: FragmentActivity,
            block: Builder.() -> Unit
        ): XPermission {
            return newInstance(fragmentActivity.supportFragmentManager, block)
        }

        fun newInstance(fragment: Fragment, block: Builder.() -> Unit): XPermission {
            return newInstance(fragment.childFragmentManager, block)
        }

        fun newInstance(manager: FragmentManager, block: Builder.() -> Unit): XPermission {
            return XPermission(manager, Builder().apply(block))
        }
    }

    private val mRequestFragment by lazy { PermissionFragment.getInstance(manager) }
    internal var requestCode = (Math.random() * 100).toInt()
    internal val permissions = builder.permissions
    fun requestResult(block: PermissionResult.() -> Unit) {
        mRequestFragment.request(requestCode, permissions) {
            block(PermissionResult(it))
        }
    }

    fun request(block: (Boolean) -> Unit) {
        requestResult {
            block(isGranted)
        }
    }

    fun checkDeniedByLastRequest(block: (List<String>) -> Unit) {
        val checkPermission = mRequestFragment.checkDeniedByLastRequest(permissions)
        block(checkPermission)
    }
}





