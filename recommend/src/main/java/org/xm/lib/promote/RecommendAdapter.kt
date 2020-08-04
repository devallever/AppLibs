package org.xm.lib.promote

import android.content.Context
import android.widget.ImageView
import org.xm.lib.core.widget.recycler.BaseRecyclerViewAdapter
import org.xm.lib.core.widget.recycler.BaseViewHolder
import org.xm.lib.core.util.getString
import com.bumptech.glide.Glide
import org.xm.lib.promote.data.Recommend

class RecommendAdapter(context: Context, resId: Int, data: MutableList<Recommend>) :
    BaseRecyclerViewAdapter<Recommend>(context, resId, data) {
    override fun bindHolder(holder: BaseViewHolder, position: Int, item: Recommend) {
        holder.setText(R.id.tvAppName, item.name)
        holder.setText(R.id.tvAppIntroduce, item.desc)
        holder.setText(
            R.id.tvAppSize, "${getString(
                R.string.recommend_size
            )}：${item.size} M"
        )
//        holder.setImageResource(R.id.ivLogo, item.iconResId)
        Glide.with(mContext).load(item.iconUrl)
            .into(holder.getView<ImageView>(R.id.ivLogo) as ImageView)
        val version = item.version
        holder.setText(
            R.id.tvAppVersion, "${getString(
                R.string.recommend_version
            )}：${item.version}"
        )
        val tag = item.tag
        if (tag.isNotEmpty()) {
            holder.setVisible(R.id.tvTag, true)
            holder.setText(R.id.tvTag, item.tag)
        }

    }
}