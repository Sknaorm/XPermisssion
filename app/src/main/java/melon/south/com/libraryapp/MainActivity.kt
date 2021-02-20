package melon.south.com.libraryapp

import android.Manifest
import android.content.Intent
import com.cn.permission.PermissionHelper
import com.cn.utils.SingleToast
import com.cn.utils.expand.getStatusBarHeight
import com.cn.utils.expand.getToolbarHeight
import com.cn.view.mvc.QuickActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : QuickActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData(intent: Intent) {
        PermissionHelper.create(this) {
            addPermission(Manifest.permission.CAMERA)
            addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }.request {

        }
    }

    override fun initView() {

    }

    override fun initEvent() {
        aaa.setOnClickListener {
            aaa.isSelected = !aaa.isSelected
        }
    }

    override fun initThird() {

    }

    override fun init() {
        SingleToast.init(applicationContext)
    }


}