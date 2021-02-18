package melon.south.com.libraryapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import com.cn.permission.PermissionHelper
import com.cn.utils.SingleToast
import com.cn.view.mvc.QuickActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : QuickActivity() {

    private val helper by lazy {
        PermissionHelper.create(this) {
            addPermission(Manifest.permission.CAMERA)
            addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData(intent: Intent) {
    }

    override fun initView() {
    }

    fun a(context: Context) {
        context.packageName
    }

    override fun initEvent() {
    }

    override fun initThird() {

    }

    override fun init() {

        SingleToast.init(applicationContext)
    }


}