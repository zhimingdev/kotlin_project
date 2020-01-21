package com.test.sandev.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.test.sandev.R
import com.test.sandev.module.VideoModule

class VideoAdapter(data : List<VideoModule>) : BaseQuickAdapter<VideoModule,BaseViewHolder>(R.layout.item_video_record,data) {
    override fun convert(helper: BaseViewHolder?, item: VideoModule?) {
    }
}