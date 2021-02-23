package melon.south.com.libraryapp

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cn.permission.PermissionHelper


class MainActivity : AppCompatActivity() {
    val a = PermissionHelper.newBuild(this)
        .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        a.request {

        }
    }

}