package com.test.sandev.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.sandev.R
import com.test.sandev.module.SqModule
import com.test.sandev.utils.CircleCrop

class SqAdapter(private val context : Context?, private val list :List<SqModule>?) : RecyclerView.Adapter<SqAdapter.SqViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SqViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_sq,parent,false)
        return SqViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: SqViewHolder, position: Int) {

        Glide.with(context).load(list!![position].headurl).transform(CircleCrop(context!!)).into(holder.headimage)
        holder.username.text = list!![position].username
        if ((list!![position].address).isNotBlank()) {
            holder.address.text = list!![position].address+"发布 :"
        }
        holder.tvContent.text = list!![position].desc
        holder.tvCollect.text = list!![position].loves
        holder.tvMsg.text = list!![position].discuss.toString()
        holder.tvTime.text = list!![position].time
//        var imageAdapter : ImgAdapter= ImgAdapter(context,list!![position].cotent)
//        holder.recyclerView.adapter = imageAdapter
        var gridelayout = GridLayoutManager(context,list!![position].cotent.size)
        holder.recyclerView.layoutManager = gridelayout
        var adapter = NewImageAdapter(context,list!![position].cotent)
        holder.recyclerView.adapter = adapter
    }


    class SqViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        val headimage : ImageView = itemview.findViewById(R.id.iv_item_head)
        val username : TextView = itemview.findViewById(R.id.tv_user_name)
        val address : TextView = itemview.findViewById(R.id.tv_add)
        val tvContent : TextView = itemview.findViewById(R.id.tv_content)
//        val recyclerView : GridView = itemview.findViewById(R.id.rv_image)
        val recyclerView : RecyclerView = itemview.findViewById(R.id.rv_image)
        val tvCollect : TextView = itemview.findViewById(R.id.tv_collect)
        val tvMsg : TextView = itemview.findViewById(R.id.tv_msg)
        val tvTime : TextView = itemview.findViewById(R.id.tv_item_time)
    }
}