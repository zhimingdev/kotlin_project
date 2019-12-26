package com.test.sandev.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.entity.CollectEntity
import com.test.sandev.ui.activity.WebActivity

class CollectAdapter(private val context: Context,val data : MutableList<CollectEntity>?) : RecyclerView.Adapter<CollectAdapter.CollectViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_collect,null)
        return CollectViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(holder: CollectViewHolder, position: Int) {
        holder.tvtitle.text = data!![position].title
        Picasso.with(context).load(data!![position].imageurl).into(holder.image)
        holder.ll.setOnClickListener {
            var intent = Intent(context,WebActivity::class.java)
            intent.putExtra("url",data!![position].url)
            context.startActivity(intent)
        }
    }


    class CollectViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var image : ImageView = itemview.findViewById(R.id.iv_collect_item)
        var tvtitle : TextView = itemview.findViewById(R.id.tv_collect_title)
        var ll : LinearLayout = itemview.findViewById(R.id.ll_collect)
    }
}