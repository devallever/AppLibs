package org.xm.lib.core.widget.recycler

interface MultiItemTypeSupport<T> {
    fun getLayoutId(itemType: Int): Int
    fun getItemViewType(position: Int, t: T): Int
}