package org.xm.lib.core.util

import android.widget.Toast
import org.xm.lib.core.base.App

object ToastUtils {
    fun show(msg: String?) {
        toast(msg)
    }

    fun showLong(msg: String?, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(App.context, msg, duration).show()
    }
}