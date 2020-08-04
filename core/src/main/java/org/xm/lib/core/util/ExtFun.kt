package org.xm.lib.core.util

import android.util.Log
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import org.jetbrains.anko.runOnUiThread
import org.xm.lib.core.base.App

private const val TAG = "ILogger"

fun log(msg: String) {
    log(TAG, msg)
}

fun log(tag: String, msg: String) {
    Log.d(tag, msg)
}

fun loge(msg: String) {
    loge(TAG, msg)
}

fun loge(tag: String, msg: String) {
    Log.e(tag, msg)
}

fun dLog(msg: String) {
    if (App.DEBUG) {
        log(msg)
    }
}

fun dLog(tag: String, msg: String) {
    if (App.DEBUG) {
        log(tag, msg)
    }
}

fun toast(msg: String?) {
    App.context.runOnUiThread {
        Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show()
    }
}

fun toast(@StringRes resId: Int) {
    App.context.runOnUiThread {
        toast(getString(resId))
    }
}

fun getString(@StringRes resId: Int): String {
    return App.context.resources.getString(resId)
}