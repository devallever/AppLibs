package org.xm.lib.image.loader

import android.content.Context
import android.widget.ImageView
import java.io.File

interface ImageLoaderProxy {
    fun load(imageView: ImageView, obj: Any)
    fun loadFromUrl(imageView: ImageView, url: String)
    fun resumeRequest(context: Context)
    fun stopRequest(context: Context)
}