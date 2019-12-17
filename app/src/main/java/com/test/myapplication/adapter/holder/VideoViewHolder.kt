package com.test.myapplication.adapter.holder

import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.test.myapplication.R
import com.test.myapplication.module.VideoModule
import com.xiao.nicevideoplayer.NiceVideoPlayer
import com.xiao.nicevideoplayer.TxVideoPlayerController
import kotlinx.android.synthetic.main.item_video.view.*

/**
 * Created by XiaoJianjun on 2017/5/21.
 */

class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var mController: TxVideoPlayerController
    var mVideoPlayer: NiceVideoPlayer = itemView.findViewById<View>(R.id.nice_video_player) as NiceVideoPlayer

    init {
        // 将列表中的每个视频设置为默认16:9的比例
        val params = mVideoPlayer.layoutParams
        params.width = itemView.resources.displayMetrics.widthPixels // 宽度为屏幕宽度
        params.height = (params.width * 9f / 16f).toInt()    // 高度为宽度的9/16
        mVideoPlayer.layoutParams = params
    }

    fun setController(controller: TxVideoPlayerController) {
        mController = controller
        mVideoPlayer.setController(mController)
    }

    fun bindData(video: VideoModule) {
        mController.setTitle(video.title)
        mController.setLenght(video.length!!)
        Glide.with(itemView.context)
                .load(video.imageurl)
                .placeholder(R.drawable.img_default)
                .crossFade()
                .into(mController.imageView())
        mVideoPlayer.setUp(video.videourl, null)
        itemView.tv_playcount.text = video.seecount
        itemView.tv_time.text = video.time
        itemView.tv_starts.text = video.starts
    }
}
