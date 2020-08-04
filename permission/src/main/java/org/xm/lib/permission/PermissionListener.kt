package org.xm.lib.permission

interface PermissionListener {
    fun onGranted(grantedList: MutableList<String>)
    fun onDenied(deniedList: MutableList<String>) {}
    fun alwaysDenied(deniedList: MutableList<String>) {}
}