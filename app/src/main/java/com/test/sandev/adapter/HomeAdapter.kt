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
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.squareup.picasso.Picasso
import com.test.sandev.MyApplication
import com.test.sandev.module.HomeBean
import com.test.sandev.R
import com.test.sandev.entity.CollectEntity
import com.test.sandev.ui.activity.LoginActivity
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
        holder.tags.text = list!![position].tags
        holder.rl.setOnClickListener {
            var intent = Intent(context, WebActivity::class.java)
            intent.putExtra("url",list!![position].descriptionurl)
            context!!.startActivity(intent)
        }

        var collectEntityDao = MyApplication.instances.daoSession!!.collectEntityDao
        var entity = collectEntityDao.load(position.toString())
        if (entity == null) {
            holder.ivCollect.setImageResource(R.mipmap.ic_uncollect)
        }else {
            holder.ivCollect.setImageResource(R.mipmap.ic_collect)
        }
        holder.ivCollect.setOnClickListener {
            var userid = SPUtils.getInstance().getInt("loginid")
            var logininfo = SPUtils.getInstance().getString("logininfo")
            if(logininfo.isNullOrBlank()) {
                var intent = Intent(context,LoginActivity::class.java)
                context!!.startActivity(intent)
            }else {
                var collectEntity = CollectEntity()
                collectEntity.id = position.toString()
                collectEntity.title = list!![position].title
                collectEntity.imageurl = list!![position].imageurl
                collectEntity.url = list!![position].descriptionurl
                collectEntity.count = list!![position].count
                collectEntity.userid = userid.toString()
                var newentity = collectEntityDao.load(position.toString())
                if (newentity != null) {
                    collectEntityDao.deleteByKey(position.toString())
                    ToastUtils.showShort("取消收藏")
                    holder.ivCollect.setImageResource(R.mipmap.ic_uncollect)
                }else {
                    var insert = collectEntityDao.insert(collectEntity)
                    if (insert != 0L) {
                        ToastUtils.showShort("收藏成功")
                        holder.ivCollect.setImageResource(R.mipmap.ic_collect)
                    }
                }
            }
        }
    }

    class ViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var time :TextView = itemview.findViewById(R.id.tv_time)
        var title :TextView = itemview.findViewById(R.id.tv_title)
        var image : ImageView = itemview.findViewById(R.id.iv_home_item)
        var number :TextView = itemview.findViewById(R.id.tv_number)
        var tags :TextView = itemview.findViewById(R.id.tv_tag)
        var rl :RelativeLayout = itemview.findViewById(R.id.rl_item)
        var ivCollect :ImageView = itemview.findViewById(R.id.iv_collect)
    }
}