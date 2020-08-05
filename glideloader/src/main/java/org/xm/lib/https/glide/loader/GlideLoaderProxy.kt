package org.xm.lib.https.glide.loader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.xm.lib.image.loader.ImageLoaderProxy

class GlideLoaderProxy: ImageLoaderProxy {

    override fun load(imageView: ImageView, obj: Any) {
        Glide.with(imageView.context).load(obj).into(imageView)
    }

    override fun loadFromUrl(imageView: ImageView, url: String) {
        Glide.with(imageView.context).load(url).into(imageView)
    }

    override fun resumeRequest(context: Context) {
        Glide.with(context).resumeRequests()
    }

    override fun stopRequest(context: Context) {
        Glide.with(context).pauseRequests()
    }
}