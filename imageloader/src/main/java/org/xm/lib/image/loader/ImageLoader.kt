package org.xm.lib.image.loader

import android.content.Context
import android.widget.ImageView

object ImageLoader {

    private var mProxy: ImageLoaderProxy = DefaultImageLoaderProxy()

    fun setProxy(proxy: ImageLoaderProxy) {
        mProxy = proxy
    }

    fun load(imageView: ImageView, obj: Any) {
        mProxy.load(imageView, obj)
    }

    fun loadFromUrl(imageView: ImageView, url: String) {
        mProxy.loadFromUrl(imageView, url)
    }

    fun pauseRequests(context: Context) {
        mProxy.stopRequest(context)
    }

    fun resumeRequests(context: Context) {
        mProxy.resumeRequest(context)
    }
}