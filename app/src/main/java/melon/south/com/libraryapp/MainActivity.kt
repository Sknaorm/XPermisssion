package melon.south.com.libraryapp

import android.Manifest
import android.content.Intent
import com.cn.work.base.WorkThread
import com.cn.permission.PermissionHelper
import com.cn.utils.SingleToast
import com.cn.view.mvc.QuickActivity
import com.cn.work.expand.doOnlyWork
import com.cn.work.expand.doWork
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope


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
        doWork<String> {
            it.onNext("1")
        }.nextOn(WorkThread.DEFAULT)
            .map {
                2
            }
            .switchMap {
                doOnlyWork(1)
            }
            .doOnNext(MainScope()) {

            }
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