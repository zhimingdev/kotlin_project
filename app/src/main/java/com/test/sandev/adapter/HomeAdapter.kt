package com.test.sandev.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.sandev.module.HomeBean
import com.test.sandev.R
import com.test.sandev.ui.activity.WebActivity

class HomeAdapter(private val context : Context?, private val list :List<HomeBean.DatasBean>?) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context.let {
            var itemview = LayoutInflater.from(context!!).inflate(R.layout.home_item,parent,false)
            return ViewHolder(itemview)
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.time.text = list!![position].time
        holder.title.text = list!![position].title
        Picasso.with(context).load(list!![position].imageurl).into(holder.image)
        holder.number.text = list!![position].count
        holder.rl.setOnClickListener {
            var intent = Intent(context, WebActivity::class.java)
            intent.putExtra("url",list!![position].descriptionurl)
            context!!.startActivity(intent)
        }
    }

    class ViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var time :TextView = itemview.findViewById(R.id.tv_time)
        var title :TextView = itemview.findViewById(R.id.tv_title)
        var image : ImageView = itemview.findViewById(R.id.iv_home_item)
        var number :TextView = itemview.findViewById(R.id.tv_number)
        var rl :RelativeLayout = itemview.findViewById(R.id.rl_item)
    }
}