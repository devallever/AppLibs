package org.xm.lib.image.loader

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Message
import android.widget.ImageView
import org.xm.lib.core.util.log
import org.xm.lib.image.loader.util.CacheMemoryStaticUtils
import org.xm.lib.image.loader.util.ConvertUtils
import org.xm.lib.image.loader.util.StringUtils
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.Future

class DefaultImageLoaderProxy : ImageLoaderProxy {

    private val cache = DoubleCache()
    private val executor = Executors.newCachedThreadPool()
    private val requestFeatureMap = ConcurrentHashMap<String, Future<*>>()

    @SuppressLint("HandlerLeak")
    private val mainHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val result = msg.obj as? LoadResult
            val bitmap = result?.bitmap
            val imageView = result?.imageView
            val key = result?.key
            if (imageView?.tag == key) {
                imageView?.setImageBitmap(bitmap)
            }
        }
    }

    override fun load(imageView: ImageView, obj: Any) {
        imageView.post {
            val key = when (obj) {
                is Int,
                is Drawable -> {
                    string2HexString(obj.hashCode().toString()) ?: ""
                }

                is String -> {
                    string2HexString(obj) ?: ""
                }
                is File -> {
                    string2HexString(obj.absolutePath) ?: ""
                }
                else -> {
                    string2HexString(obj.hashCode().toString()) ?: ""
                }
            }

            val bitmap = cache.get(key)
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                return@post
            }

            val width = imageView.width
            val height = imageView.height
            imageView.tag = key
            executor.execute {
                val compressBitmap = ImageCompress.compress(obj, width, height)
                val loadResult = LoadResult(imageView, key, compressBitmap)
                val msg = Message()
                msg.obj = loadResult
                mainHandler.sendMessage(msg)
                if (compressBitmap != null) {
                    cache.put(key, compressBitmap)
                }
            }
        }
    }

    override fun loadFromUrl(imageView: ImageView, urlString: String) {
        if (StringUtils.isEmpty(urlString)) {
            return
        }

        imageView.post {
            val key = string2HexString(urlString) ?: ""
            val bitmap = cache.get(key)
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                return@post
            }
            log("内存和磁盘缓存为空")
            val width = imageView.width
            val height = imageView.height

            val originBitmap = CacheMemoryStaticUtils.get<Bitmap>(urlString.hashCode().toString())
            if (originBitmap != null) {
                val compressBitmap = ImageCompress.compress(originBitmap, width, height)
                if (compressBitmap != null) {
                    cache.put(key, compressBitmap)
                    return@post
                }
            }
            log("请求缓存为空")

            imageView.tag = urlString
            val request = executor.submit {
                log("在线程池中执行")
                var httpURLConnection: HttpURLConnection? = null
                try {
                    val url = URL(urlString)
                    httpURLConnection = url.openConnection() as HttpURLConnection
                    httpURLConnection.connectTimeout = 8000
                    httpURLConnection.readTimeout = 8000
                    val inputStream = httpURLConnection.inputStream
                    val bytes = ConvertUtils.inputStream2Bytes(inputStream)
                    val originBitmap =
                        ConvertUtils.bytes2Bitmap(bytes)
                    CacheMemoryStaticUtils.put(urlString.hashCode().toString(), originBitmap)

                    val composeBitmap = ImageCompress.compress(bytes, width, height)
                    if (composeBitmap != null) {
                        cache.put(key, composeBitmap)
                    }
                    val message = Message()
                    val loadResult = LoadResult(imageView, urlString, composeBitmap)
                    message.obj = loadResult
                    mainHandler.sendMessage(message)
                    requestFeatureMap.remove(urlString)
                } catch (ioe: IOException) {
                    ioe.printStackTrace()
                } finally {
                    httpURLConnection?.disconnect()
                }
            }

            requestFeatureMap[urlString] = request
        }
    }

    override fun resumeRequest(context: Context) {
    }

    override fun stopRequest(context: Context) {
        requestFeatureMap.map {
            it.value.cancel(true)
        }
    }


    private fun string2HexString(content: String): String? {
        return try {
            val messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.update(content.toByteArray())
            bytesToHexString(messageDigest.digest())
        } catch (e: NoSuchAlgorithmException) {
            content.hashCode().toString()
        }
    }


    private fun bytesToHexString(bytes: ByteArray): String? {
        val stringBuilder = StringBuilder()
        for (i in bytes.indices) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                stringBuilder.append('0')
            }
            stringBuilder.append(hex)
        }
        return stringBuilder.toString()
    }

    class LoadResult(
        val imageView: ImageView? = null,
        val key: String? = null,
        val bitmap: Bitmap? = null
    )
}