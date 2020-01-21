package com.test.sandev.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import com.bumptech.glide.Glide
import com.test.sandev.R
import com.test.sandev.constans.UrlConstans
import com.test.sandev.module.HomeNewBean
import com.test.sandev.ui.activity.VideoDetailActivity
import com.test.sandev.utils.recyclerview.ViewHolder
import com.test.sandev.utils.recyclerview.adapter.CommonAdapter

/**
 * Created by xuhao on 2017/12/11.
 * desc:
 */
class WatchHistoryAdapter(private var context: Context, dataList: ArrayList<HomeNewBean.Issue.Item>, layoutId: Int)
    : CommonAdapter<HomeNewBean.Issue.Item>(context, dataList, layoutId) {


    //绑定数据
    override fun bindData(holder: ViewHolder, data: HomeNewBean.Issue.Item, position: Int) {
        with(holder) {
            setText(R.id.tv_title, data.data?.title!!)
            setText(R.id.tv_tag, "#${data.data.category} / ${durationFormat(data.data.duration)}")
            setImagePath(R.id.iv_video_small_card, object : ViewHolder.HolderImageLoader(data.data.cover.detail) {
                override fun loadImage(iv: ImageView, path: String) {
                    Glide.with(mContext)
                            .load(path)
                            .into(iv)
                }
            })
        }
        holder.getView<TextView>(R.id.tv_title).setTextColor(ContextCompat.getColor(context,android.R.color.black))
        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(context as Activity, holder.getView(R.id.iv_video_small_card), data)
        })
    }


    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeNewBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(UrlConstans.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair<View, String>(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
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