package com.test.sandev.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.module.RecordModule
import com.test.sandev.utils.CircleCrop
import com.test.sandev.utils.CircleTransform

class DetaiGuest2Adapter(private val context: Context, private val list: List<RecordModule.GuestinfoBean.GuestInfoBean.ThreelistBeanX>) : RecyclerView.Adapter<DetaiGuest2Adapter.DetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_detail,null)
        return DetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        Picasso.with(context).load(list[position].headurl)
                .transform(CircleTransform())
                .into(holder.imghead)
        holder.tvname.text = list[position].name
    }

    class DetailViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var imghead = itemview.findViewById(R.id.iv_head) as ImageView
        var tvname = itemview.findViewById<TextView>(R.id.tv_item_name)
    }
}