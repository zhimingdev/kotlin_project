package com.test.sandev.adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.module.HomeNewBean
import com.test.sandev.utils.CircleTransform
import com.test.sandev.utils.recyclerview.MultipleType
import com.test.sandev.utils.recyclerview.ViewHolder
import com.test.sandev.utils.recyclerview.adapter.CommonAdapter

/**
 * Created by xuhao on 2017/11/25.
 * desc: 视频详情
 */

class VideoDetailAdapter(mContext: Context, data: ArrayList<HomeNewBean.Issue.Item>) :
        CommonAdapter<HomeNewBean.Issue.Item>(mContext, data, object : MultipleType<HomeNewBean.Issue.Item> {
            override fun getLayoutId(item: HomeNewBean.Issue.Item, position: Int): Int {
                return when {
                    position == 0 ->
                        R.layout.item_video_detail_info

                    data[position].type == "textCard" ->
                        R.layout.item_video_text_card

                    data[position].type == "videoSmallCard" ->
                        R.layout.item_video_small_card
                    else ->
                        throw IllegalAccessException("Api 解析出错了，出现其他类型")
                }
            }
        }) {


    /**
     * 添加视频的详细信息
     */
    fun addData(item: HomeNewBean.Issue.Item) {
        mData.clear()
        notifyDataSetChanged()
        mData.add(item)
        notifyItemInserted(0)

    }

    /**
     * 添加相关推荐等数据 Item
     */
    fun addData(item: ArrayList<HomeNewBean.Issue.Item>) {
        mData.addAll(item)
        notifyItemRangeInserted(1, item.size)

    }

    /**
     * Kotlin的函数可以作为参数，写callback的时候，可以不用interface了
     */

    private var mOnItemClickRelatedVideo: ((item: HomeNewBean.Issue.Item) -> Unit)? = null


    fun setOnItemDetailClick(mItemRelatedVideo: (item: HomeNewBean.Issue.Item) -> Unit) {
        this.mOnItemClickRelatedVideo = mItemRelatedVideo
    }


    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: HomeNewBean.Issue.Item, position: Int) {
        when {
            position == 0 -> setVideoDetailInfo(data, holder)

            data.type == "textCard" -> {
                holder.setText(R.id.tv_text_card, data.data?.text!!)
            }
            data.type == "videoSmallCard" -> {
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
                // 判断onItemClickRelatedVideo 并使用
                holder.itemView.setOnClickListener { mOnItemClickRelatedVideo?.invoke(data) }

            }
            else -> throw IllegalAccessException("Api 解析出错了，出现其他类型")
        }
    }

    /**
     * 设置视频详情数据
     */
    private fun setVideoDetailInfo(data: HomeNewBean.Issue.Item, holder: ViewHolder) {
        data.data?.title?.let { holder.setText(R.id.tv_title, it) }
        //视频简介
        data.data?.description?.let { holder.setText(R.id.expandable_text, it) }
        //标签
        holder.setText(R.id.tv_tag, "#${data.data?.category} / ${durationFormat(data.data?.duration)}")
        //喜欢
        holder.setText(R.id.tv_action_favorites, data.data?.consumption?.collectionCount.toString())
        //分享
        holder.setText(R.id.tv_action_share, data.data?.consumption?.shareCount.toString())
        //评论
        holder.setText(R.id.tv_action_reply, data.data?.consumption?.replyCount.toString())

        if (data.data?.author != null) {
            with(holder) {
                setText(R.id.tv_author_name, data.data.author.name)
                setText(R.id.tv_author_desc, data.data.author.description)
                setImagePath(R.id.iv_avatar, object : ViewHolder.HolderImageLoader(data.data.author.icon) {
                    override fun loadImage(iv: ImageView, path: String) {
                        //加载头像
                        Picasso.with(mContext)
                                .load(path)
                                .transform(CircleTransform())
                                .into(iv)
                    }
                })
            }
        } else {
            holder.setViewVisibility(R.id.layout_author_view, View.GONE)
        }

        with(holder) {
            getView<TextView>(R.id.tv_action_favorites).setOnClickListener {
            }
            getView<TextView>(R.id.tv_action_share).setOnClickListener {
                val sendIntent = Intent()
                sendIntent.action = ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_STREAM, data.data!!.playUrl)
                sendIntent.type = "audio/*"
                mContext.startActivity(Intent.createChooser(sendIntent, ""))
            }
            getView<TextView>(R.id.tv_action_reply).setOnClickListener {
            }
            getView<TextView>(R.id.tv_attention).setOnClickListener {
                holder.setText(R.id.tv_attention,"已关注")
            }
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
