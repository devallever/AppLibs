package org.xm.lib.core.base

import android.os.Bundle

/**
 *
 * @author allever
 * @date 18-2-28
 */
abstract class AbstractMvpActivity<V, P : AbstractPresenter<V>?> : AbstractActivity() {

    protected var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
        //view 与 Presenter 关联
        mPresenter?.attachView(this as? V)
    }

    override fun onDestroy() {
        mPresenter?.detachView()
        super.onDestroy()
    }

    protected abstract fun createPresenter(): P
}