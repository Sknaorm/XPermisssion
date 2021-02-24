package melon.south.com.libraryapp

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cn.permission.XPermission


class MainActivity : AppCompatActivity() {
    val a = XPermission.newInstance(this) {
        addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        a.requestResult {
            val data = this.data
            val granted = this.isGranted
            val neverRefuse = this.isNeverRefuse
        }
    }

}