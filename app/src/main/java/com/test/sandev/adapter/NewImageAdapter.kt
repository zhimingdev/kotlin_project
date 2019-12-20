package com.test.sandev.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.module.SqModule
import com.test.sandev.utils.CircleCornerForm

class NewImageAdapter(private val context: Context, private val images: List<SqModule.CotentBean>) : RecyclerView.Adapter<NewImageAdapter.NewViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewholder {
        var view = LayoutInflater.from(context).inflate(R.layout.new_imte_image,null)
        return NewViewholder(view)
    }

    override fun getItemCount(): Int {
        return images!!.size
    }

    override fun onBindViewHolder(holder: NewViewholder, position: Int) {
        Picasso.with(context)
                .load(images!![position].imageurl)
                .transform(CircleCornerForm(context))
                .into(holder.imageView!!)
    }


    class NewViewholder(itemview : View) : RecyclerView.ViewHolder(itemview){
        var imageView: ImageView? = itemview.findViewById(R.id.iv_new)
    }
}
