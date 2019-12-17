package com.test.sandev.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import com.test.sandev.R
import com.test.sandev.adapter.holder.VideoViewHolder
import com.test.sandev.module.VideoModule
import com.xiao.nicevideoplayer.TxVideoPlayerController

/**
 * Created by XiaoJianjun on 2017/5/21.
 */

class VideoAdapter(private val mContext: Context, private val data: List<VideoModule>?) : RecyclerView.Adapter<VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false)
        val holder = VideoViewHolder(itemView)
        val controller = TxVideoPlayerController(mContext)
        holder.setController(controller)
        return holder
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        data!![position].let {
            holder.bindData(it)
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }
}
