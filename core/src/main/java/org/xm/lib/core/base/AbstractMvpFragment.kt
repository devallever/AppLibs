package org.xm.lib.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Mac on 18/3/1.
 */
abstract class AbstractMvpFragment<V, P : AbstractPresenter<V>?> : AbstractFragment() {

    protected var mPresenter: P? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as? V)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        mPresenter?.detachView()
        super.onDestroyView()
    }

    protected abstract fun createPresenter(): P
}