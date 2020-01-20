package com.test.sandev.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.constans.UrlConstans
import com.test.sandev.module.HomeNewBean
import com.test.sandev.utils.recyclerview.ViewHolder
import com.test.sandev.utils.recyclerview.adapter.CommonAdapter

/**
 * Created by xuhao on 2017/11/30.
 * desc:分类详情Adapter
 */
class CategoryDetailAdapter(context: Context, dataList: ArrayList<HomeNewBean.Issue.Item>, layoutId: Int)
    : CommonAdapter<HomeNewBean.Issue.Item>(context, dataList, layoutId) {


    fun addData(dataList: ArrayList<HomeNewBean.Issue.Item>) {
        this.mData.addAll(dataList)
        notifyDataSetChanged()
    }


    override fun bindData(holder: ViewHolder, data: HomeNewBean.Issue.Item, position: Int) {
        setVideoItem(holder, data)
    }

    /**
     * 加载 content item
     */
    private fun setVideoItem(holder: ViewHolder, item: HomeNewBean.Issue.Item) {
        val itemData = item.data
        val cover = itemData?.cover?.feed?:""
        // 加载封页图
        Picasso.with(mContext)
                .load(cover)
                .into(holder.getView(R.id.iv_image) as ImageView)
        holder.setText(R.id.tv_title, itemData?.title?:"")

        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)

        holder.setText(R.id.tv_tag, "#${itemData?.category}/$timeFormat")

        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_image), item)
        })


    }

    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeNewBean.Issue.Item) {
//        val intent = Intent(activity, VideoDetailActivity::class.java)
//        intent.putExtra(UrlConstans.BUNDLE_VIDEO_DATA, itemData)
//        intent.putExtra(VideoDetailActivity.Companion.TRANSITION, true)
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            val pair = Pair<View, String>(view, VideoDetailActivity.IMG_TRANSITION)
//            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    activity, pair)
//            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
//        } else {
//            activity.startActivity(intent)
//            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
//        }
    }

    fun durationFormat(duration: Long?): String {
        val minute = duration!! / 60
        val second = duration % 60
        return if (minute <= 9) {
            if (second <= 9) {
                "0$minute' 0$second''"
            } else {
                "0$minute' $second''"
            }
        } else {
            if (second <= 9) {
                "$minute' 0$second''"
            } else {
                "$minute' $second''"
            }
        }
    }

}