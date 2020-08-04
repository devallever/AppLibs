package org.xm.lib.core.widget.recycler

interface ItemListener {
    fun onItemClick(position: Int, holder: BaseViewHolder)
    fun onItemLongClick(position: Int, holder: BaseViewHolder): Boolean = false
}