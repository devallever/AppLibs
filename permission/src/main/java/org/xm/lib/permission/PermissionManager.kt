package org.xm.lib.permission

import android.app.Activity
import android.content.DialogInterface
import androidx.fragment.app.Fragment
import com.yanzhenjie.permission.AndPermission

import org.xm.lib.core.base.App

object PermissionManager {
    fun request(listener: PermissionListener, vararg permissions: String) {
        AndPermission.with(App.context)
            .runtime()
            .permission(permissions)
            .onGranted {
                listener.onGranted(it)
            }
            .onDenied {
                if (AndPermission.hasAlwaysDeniedPermission(App.context, it)) {
                    listener.alwaysDenied(it)
                } else {
                    listener.onDenied(it)
                }
            }
            .start()
    }

    fun hasPermissions(vararg permissions: String): Boolean {
        return PermissionCompat.hasPermission(App.context, *permissions)
    }

    fun alwaysDenyPermissions(activity: Activity, vararg permissions: String): Boolean =
        PermissionCompat.hasAlwaysDeniedPermission(
            activity,
            *permissions
        )

    fun jumpPermissionSetting(
        activity: Activity?,
        requestCode: Int,
        cancelListener: DialogInterface.OnClickListener
    ) {
        FastPermission.openPermissionManually(
            activity,
            requestCode,
            cancelListener
        )
    }
}