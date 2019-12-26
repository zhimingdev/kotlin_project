package com.test.sandev.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.module.MatchModule
import com.test.sandev.ui.activity.MatchDetailActivity
import com.test.sandev.utils.CircleCrop
import com.test.sandev.utils.CircleTransform

class MatchRecordAdapter(private val context: Context,val data :List<MatchModule>?) : RecyclerView.Adapter<MatchRecordAdapter.MatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_match,null)
        return MatchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        Picasso.with(context).load(data!![position].hostteam).transform(CircleTransform()).into(holder.ivleft)
        Picasso.with(context).load(data!![position].guestteam).transform(CircleTransform()).into(holder.ivright)
        holder.tags.text = data!![position].tags
        holder.tvleftname.text = data!![position].hostname
        holder.tvrightname.text = data!![position].vuestname
        holder.tvleftcoint.text = data!![position].hostcount
        holder.tvrightcount.text = data!![position].guestcount
        holder.cardView.setOnClickListener {
            var intent = Intent(context, MatchDetailActivity::class.java)
            intent.putExtra("index",position+1)
            context!!.startActivity(intent)
        }
    }

    class MatchViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var tags = itemview.findViewById<TextView>(R.id.tv_tags)
        var tvleftname = itemview.findViewById<TextView>(R.id.tv_team_name)
        var tvrightname = itemview.findViewById<TextView>(R.id.tv_team_name_right)
        var tvleftcoint = itemview.findViewById<TextView>(R.id.tv_left_count)
        var tvrightcount = itemview.findViewById<TextView>(R.id.tv_right_count)
        var ivleft = itemview.findViewById<ImageView>(R.id.iv_left_team)
        var ivright = itemview.findViewById<ImageView>(R.id.iv_right_team)
        var cardView = itemView.findViewById<CardView>(R.id.cv)
    }
}