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

class InfoItem(private val context: Context,private val list: List<RecordModule.PlayinfoBean>) : RecyclerView.Adapter<InfoItem.InfoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.info_item,null)
        return InfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        Picasso.with(context).load(list[position].hostimg).transform(CircleTransform()).into(holder.hostheadimage)
        Picasso.with(context).load(list[position].guestimg).transform(CircleTransform()).into(holder.guestheadimage)

        holder.hosttvname.text = list[position].hostname
        holder.guesttvname.text = list[position].guestname

        holder.hosttvaddress.text = list[position].hostaddress
        holder.guesttvaddress.text = list[position].guestaddress

        holder.hosttvcount.text = list[position].hostcoint
        holder.guesttvcount.text = list[position].guestcoint
    }


    class InfoViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var hostheadimage = itemview.findViewById<ImageView>(R.id.iv_info)
        var hosttvaddress = itemview.findViewById<TextView>(R.id.tv_address)
        var hosttvname = itemview.findViewById<TextView>(R.id.tv_host_qf_name)
        var hosttvcount = itemview.findViewById<TextView>(R.id.tv_host_count)
        var guestheadimage = itemview.findViewById<ImageView>(R.id.iv_end_info)
        var guesttvaddress = itemview.findViewById<TextView>(R.id.tv_guest_address)
        var guesttvname = itemview.findViewById<TextView>(R.id.tv_qf_guestname)
        var guesttvcount = itemview.findViewById<TextView>(R.id.tv_guest_count)
    }
}