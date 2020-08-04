package org.xm.lib.core.util

object LogUtils {

    fun d(tag: String, msg: String) {
        log(tag, msg)
    }

    fun d(msg: String) {
        log(msg)
    }

    fun e(tag: String, msg: String) {
        loge(tag, msg)
    }

    fun e(msg: String) {
        loge(msg)
    }
}