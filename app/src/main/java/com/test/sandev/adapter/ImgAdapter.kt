package com.test.sandev.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.test.sandev.R
import com.test.sandev.module.SqModule

class ImgAdapter(private val context: Context, private val images: List<SqModule.CotentBean>) : BaseAdapter() {

    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(position: Int): Any {
        return images[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val myViewholder: MyViewholder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.imte_image, null)
            myViewholder = MyViewholder()
            myViewholder.imageView = convertView!!.findViewById(R.id.iv)
            convertView.tag = myViewholder
        } else {
            myViewholder = convertView.tag as MyViewholder
        }
        Glide.with(context).load(images!![position].imageurl).into(myViewholder.imageView!!)
        return convertView
    }

    internal inner class MyViewholder {
        var imageView: ImageView? = null
    }
}
