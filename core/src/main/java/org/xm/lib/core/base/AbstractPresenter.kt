package org.xm.lib.core.base

import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * Created by allever on 18-2-28.
 */
abstract class AbstractPresenter<V> {

    //View类(Activity Fragment, View(控件))接口弱引用
    protected var mViewRef: Reference<V?>? = null

    fun attachView(view: V?) {
        mViewRef = WeakReference(view)
    }

    protected val view: V?
        protected get() = mViewRef?.get()

    val isAttachedView: Boolean
        get() = mViewRef != null && mViewRef?.get() != null

    fun detachView() {
        mViewRef?.clear()
        mViewRef = null
    }
}