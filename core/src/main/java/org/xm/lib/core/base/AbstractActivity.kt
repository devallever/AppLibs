package org.xm.lib.core.base

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.xm.lib.core.R
import org.xm.lib.core.helper.ActivityHelper
import org.xm.lib.core.util.log
import org.xm.lib.core.util.toast
import java.lang.ref.WeakReference

abstract class AbstractActivity : AppCompatActivity() {
    protected val mHandler = Handler(Looper.getMainLooper())
    private var mWeakRefActivity: WeakReference<Activity>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log(this.javaClass.simpleName)
        mWeakRefActivity = WeakReference(this)
        ActivityHelper.add(mWeakRefActivity)
    }

    override fun onDestroy() {
        ActivityHelper.remove(mWeakRefActivity)
        super.onDestroy()
    }

    private var firstPressedBackTime = 0L
    protected fun checkExit(runnable: Runnable? = null) {
        if (System.currentTimeMillis() - firstPressedBackTime < 2000) {
            runnable?.run()
            super.onBackPressed()
        } else {
            toast(getString(R.string.core_click_again_to_exit))
            firstPressedBackTime = System.currentTimeMillis()
        }
    }

    protected fun setVisibility(view: View, show: Boolean) {
        if (show) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}