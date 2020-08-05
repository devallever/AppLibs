package org.xm.lib.image.loader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import org.xm.lib.core.base.App
import org.xm.lib.core.util.log
import org.xm.lib.image.loader.util.FileUtils
import org.xm.lib.image.loader.util.ImageUtils
import java.io.File
import java.io.FileDescriptor
import java.io.InputStream

/**
 * 图片压缩
 */
object ImageCompress {

    fun compress(obj: Any, requestWidth: Int, requestHeight: Int): Bitmap? {
        val options = obtainOption(obj)
        options.inSampleSize = calculateSampleSize(options, requestWidth, requestHeight)
        options.inJustDecodeBounds = false
        return obtainBitmap(obj, options)
    }

    private fun obtainOption(obj: Any): BitmapFactory.Options {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        decode(obj, options)
        return options
    }

    private fun obtainBitmap(obj: Any, options: BitmapFactory.Options): Bitmap? =
        decode(obj, options)

    private fun decode(obj: Any, options: BitmapFactory.Options): Bitmap? {
        return when (obj) {
            is Int -> {
                BitmapFactory.decodeResource(App.context.resources, obj, options)
            }
            is String -> {
                if (FileUtils.isFileExists(obj)) {
                    BitmapFactory.decodeFile(obj, options)
                } else {
                    null
                }
            }
            is File -> {
                if (obj.exists()) {
                    BitmapFactory.decodeFile(obj.absolutePath, options)
                } else {
                    null
                }
            }
            is InputStream -> {
                BitmapFactory.decodeStream(obj, null, options)
            }
            is ByteArray -> {
                BitmapFactory.decodeByteArray(obj, 0, obj.size, options)
            }
            is FileDescriptor -> {
                BitmapFactory.decodeFileDescriptor(obj, null, options)
            }
            is Drawable -> {
                val bytes = ImageUtils.drawable2Bytes(obj)
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
            }
            is Bitmap -> {
                val bytes = ImageUtils.bitmap2Bytes(obj)
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
            }
            else -> {
                return null
            }
        }
    }

    private fun calculateSampleSize(
        options: BitmapFactory.Options,
        requestWidth: Int,
        requestHeight: Int
    ): Int {
        if (requestWidth == 0 || requestHeight == 0) {
            return 1
        }
        val width = options.outWidth
        val height = options.outHeight
        log("calculateSampleSize: outWidth = $width\noutHeight = $height")
        var inSampleSize = 1
        log("calculateSampleSize: inSampleSize = $inSampleSize")
        if (width > requestWidth || height > requestHeight) {
            val halfWidth = width / 2
            val halfHeight = height / 2
            while (halfWidth / inSampleSize > requestWidth && halfHeight / inSampleSize > requestHeight) {
                inSampleSize *= 2
                log("calculateSampleSize: inSampleSize = $inSampleSize")
            }
        }
        return inSampleSize
    }
}