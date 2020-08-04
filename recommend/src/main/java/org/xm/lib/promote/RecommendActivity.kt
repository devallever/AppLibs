package org.xm.lib.promote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.xm.lib.core.base.AbstractActivity
import org.xm.lib.core.widget.recycler.BaseViewHolder
import org.xm.lib.core.widget.recycler.ItemListener
import org.xm.lib.core.util.FileUtils
import org.xm.lib.core.util.SystemUtils
import org.xm.lib.core.util.toast
import com.google.gson.Gson
import org.xm.lib.promote.data.Recommend
import org.xm.lib.promote.data.RecommendBean
import java.io.File

class RecommendActivity : AbstractActivity(), View.OnClickListener {

    private var mRecommendData = mutableListOf<Recommend>()
    private lateinit var mRvRecommendList: RecyclerView
    private var mAdapter: RecommendAdapter? = null
    private var mUmengChannel = ""

    private var mRecommendListener = object :
        RecommendListener {
        override fun onSuccess(data: MutableList<Recommend>) {
            mRecommendData.clear()
            mRecommendData.addAll(data)
            mAdapter?.notifyDataSetChanged()
        }

        override fun onFail() {
            toast("No Recommend")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)

        mUmengChannel = intent?.getStringExtra(EXTRA_CHANNEL) ?: ""

        findViewById<View>(R.id.iv_back).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_label).text = getString(
            R.string.recommend
        )

        mRecommendData.addAll(RecommendGlobal.recommendData)
        mAdapter = RecommendAdapter(
            this,
            R.layout.item_recommend,
            mRecommendData
        )
        mRvRecommendList = findViewById(R.id.rvRecommendList)
        mRvRecommendList.layoutManager = LinearLayoutManager(this)
        mRvRecommendList.adapter = mAdapter
        mAdapter?.setItemListener(object :
            ItemListener {
            override fun onItemClick(position: Int, holder: BaseViewHolder) {
                //如果安装了谷歌商店，则打开google商店
                val item = mRecommendData[position]
                val url = RecommendGlobal.getItemUrl(item)
                SystemUtils.openUrl(this@RecommendActivity, url)
            }
        })

        if (RecommendGlobal.recommendData.isEmpty()) {
            RecommendGlobal.getRecommendData(mUmengChannel, mRecommendListener)
        }

//        if (BuildConfig.DEBUG) {
//            getLocalRecommendData()
//        } else {
//            if (RecommendGlobal.recommendData.isEmpty()) {
//                getRecommendData()
//            }
//        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                finish()
            }
        }
    }

    private fun getLocalRecommendData() {
        val path =
            Environment.getExternalStorageDirectory().absolutePath + File.separator + "recommend.json"
        val recommendData = FileUtils.readTextFile(path)
        try {
            val gson = Gson()
            val recommend = gson.fromJson(recommendData, RecommendBean::class.java)
            RecommendGlobal.handleRecommendData(recommend, mUmengChannel, mRecommendListener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val EXTRA_CHANNEL = "EXTRA_CHANNEL"
        fun start(context: Context, channel: String) {
            val intent = Intent(context, RecommendActivity::class.java)
            intent.putExtra(EXTRA_CHANNEL, channel)
            context.startActivity(intent)
        }
    }
}