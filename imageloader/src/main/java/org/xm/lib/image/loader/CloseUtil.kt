package org.xm.lib.image.loader

import java.io.Closeable
import java.io.IOException

/**
 * Created by allever on 17-10-13.
 */
object CloseUtil {
    fun closeQuickly(closeable: Closeable?) {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (ioe: IOException) {
                ioe.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}