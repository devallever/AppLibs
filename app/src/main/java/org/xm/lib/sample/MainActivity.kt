package org.xm.lib.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.xm.lib.core.base.App
import org.xm.lib.core.util.log
import org.xm.lib.image.loader.ImageLoader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        ImageLoader.setProxy(GlideLoaderProxy())
        val int = R.drawable.baidu
//        ivImage.setImageBitmap(BitmapFactory.decodeResource(resources, int))
        ImageLoader.loadFromUrl(ivImage, "http://pic1.win4000.com/pic/a/e6/c4f0388de0.jpg")
//        log(App.context.cacheDir.path)
    }

    override fun onResume() {
        super.onResume()
        ImageLoader.resumeRequests(this)
    }

    override fun onStop() {
        super.onStop()
        ImageLoader.pauseRequests(this)
    }
}