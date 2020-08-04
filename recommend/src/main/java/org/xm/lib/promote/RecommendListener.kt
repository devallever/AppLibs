package org.xm.lib.promote

import org.xm.lib.promote.data.Recommend

interface RecommendListener {
    fun onSuccess(data: MutableList<Recommend>)
    fun onFail()
}