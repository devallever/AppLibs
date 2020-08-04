package org.xm.lib.core.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import org.xm.lib.core.BuildConfig

open class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        val DEBUG = BuildConfig.DEBUG
    }
}