package com.cn.permission

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

internal class PermissionFragment : Fragment() {
    companion object {
        fun getInstance(manager: FragmentManager): PermissionFragment {
            var tag = manager.findFragmentByTag("PermissionFragment") as? PermissionFragment
            if (tag == null) {
                tag = PermissionFragment()
                manager.beginTransaction()
                    .add(tag, "permissionFragment")
                    .commitNowAllowingStateLoss()
                manager.executePendingTransactions()
            }
            return tag
        }
    }

    private val mRequestPermissionMap = hashMapOf<Int, (List<PermissionData>) -> Unit>()

    /**
     * 请求权限
     */
    fun request(code: Int, list: List<String>, block: (List<PermissionData>) -> Unit) {
        this.requestPermissions(list.toTypedArray(), code)
        mRequestPermissionMap[code] = block
    }

    /**
     * 检查有没有上次请求被拒的权限
     */
    fun checkDeniedByLastRequest(list: List<String>): List<String> {
        val context = checkNotNull(activity)
        return list.filter {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_DENIED
                    && ActivityCompat.shouldShowRequestPermissionRationale(
                context,
                it
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val block = mRequestPermissionMap[requestCode] ?: return
        val resultData = arrayListOf<PermissionData>()
        var permission: String
        var grantResult: Int
        var result: PermissionData
        for (i in permissions.indices) {
            permission = permissions[i]
            grantResult = grantResults[i]
            val isGranted = grantResult == PackageManager.PERMISSION_GRANTED
            result = PermissionData(
                permission,
                isGranted,
                if (isGranted) false else !shouldShowRequestPermissionRationale(permission)
            )
            resultData.add(result)
        }
        mRequestPermissionMap.remove(requestCode)
        block(resultData)
    }
}
