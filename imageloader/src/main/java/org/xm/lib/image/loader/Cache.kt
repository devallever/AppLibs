package org.xm.lib.image.loader

import android.graphics.Bitmap
import android.util.LruCache
import org.xm.lib.core.base.App
import org.xm.lib.core.util.log
import org.xm.lib.image.loader.util.CacheDiskStaticUtils

interface Cache {
    fun put(key: String, value: Bitmap)
    fun get(key: String): Bitmap?
    fun remove(key: String)
}

class MemoryCache : Cache {
    //取系统内存的1/8做缓存
    private val cacheSize = Runtime.getRuntime().maxMemory() / 1024 / 8
    private val cache: LruCache<String, Bitmap> = LruCache(cacheSize.toInt())

    override fun put(key: String, value: Bitmap) {
        cache.put(key, value)
    }

    override fun get(key: String): Bitmap? {
        return cache.get(key)
    }

    override fun remove(key: String) {
        cache.remove(key)
    }

}

class DiskCache : Cache {

    private lateinit var cache: DiskLruCache

    //100MB
    //private val defaultCacheSize = 100 * 1024 * 1024
    private val cacheDir = App.context.cacheDir

    init {
        val cacheSize = cacheDir.usableSpace / 8
        try {
            cache = DiskLruCache.open(
                cacheDir,
                1,
                1,
                cacheSize
            )
        } catch (e: Exception) {
            e.printStackTrace()
            log("初始化磁盘缓存失败")
        }
    }

    override fun put(key: String, value: Bitmap) {
        CacheDiskStaticUtils.put(key, value)
    }

    override fun get(key: String): Bitmap? {
        return CacheDiskStaticUtils.getBitmap(key)
    }

    override fun remove(key: String) {
        CacheDiskStaticUtils.remove(key)
    }

}

class DoubleCache : Cache {
    private val memoryCache = MemoryCache()
    private val diskCache = DiskCache()

    override fun put(key: String, value: Bitmap) {
        memoryCache.put(key, value)
        diskCache.put(key, value)
    }

    override fun get(key: String): Bitmap? = memoryCache.get(key) ?: diskCache.get(key)

    override fun remove(key: String) {
        memoryCache.remove(key)
        diskCache.remove(key)
    }
}