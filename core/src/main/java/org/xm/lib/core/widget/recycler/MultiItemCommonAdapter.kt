package org.xm.lib.core.widget.recycler

import android.content.Context
import android.view.ViewGroup

/**
 * @author allever
 */
abstract class MultiItemCommonAdapter<T>(
    context: Context,
    data: MutableList<T>,
    protected var mMultiItemTypeSupport: MultiItemTypeSupport<T>
) : BaseRecyclerViewAdapter<T>(context, -1, data) {

    override fun getItemViewType(position: Int): Int {
        return mMultiItemTypeSupport.getItemViewType(position, mData[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutId = mMultiItemTypeSupport.getLayoutId(viewType)
        return BaseViewHolder.getHolder(mContext, parent, layoutId)
    }

}