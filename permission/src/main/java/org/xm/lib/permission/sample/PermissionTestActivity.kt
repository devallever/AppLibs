package org.xm.lib.permission.sample

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.xm.lib.permission.PermissionListener
import org.xm.lib.permission.PermissionManager
import org.xm.lib.core.util.log
import org.xm.lib.core.util.toast
import org.xm.lib.permission.R

class PermissionTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_test)
        PermissionManager.request( object :
            PermissionListener {
            override fun onGranted(grantedList: MutableList<String>) {
                toast("onGranted")
                log("onGranted")
            }

            override fun onDenied(deniedList: MutableList<String>) {
                toast("onDenied")
                log("onDenied")
            }

            override fun alwaysDenied(deniedList: MutableList<String>) {
                log("alwaysDenied")
            }

        }, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}