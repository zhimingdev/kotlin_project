package com.test.sandev.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.GuanZModule
import com.test.sandev.module.LanqZhuModule
import com.test.sandev.ui.activity.JiFenActivity
import com.test.sandev.ui.activity.LQDetailActivity
import com.test.sandev.ui.activity.LoginActivity
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.CircleCrop
import com.test.sandev.utils.CircleTransform
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.fragment_gaunzhu.*
import kotlinx.android.synthetic.main.item_gz.*

class GuanZhuFragment(private var type :Int) : BaseFragment(){

    val network by lazy { NetWork() }
    var data : List<GuanZModule>? = null
    var hist : List<GuanZModule.TablistBean>? = null
    var hisAdapter : HisAdapter? = null
    var adapter : GZAdapter? = null

    var basdata : List<LanqZhuModule>? = null
    var basadapter : LanZAdpter? = null

    companion object{
        fun getInstance( position :Int) :GuanZhuFragment{
            val fragemnt = GuanZhuFragment(position)
            return fragemnt
        }
    }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_gaunzhu,null)
        return view
    }

    override fun initSandevDate() {
        getGuanzhu()
    }

    private fun getGuanzhu() {
        when(type) {
            0 -> {
                network.getApi(Api::class.java).getZUguanzhu()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : ApiBaseResponse<List<GuanZModule>>(activity!!){
                            override fun onSuccess(t: List<GuanZModule>?) {
                                var linearLayoutManager = LinearLayoutManager(context)
                                rv_gz.layoutManager = linearLayoutManager
                                adapter = GZAdapter()
                                rv_gz.adapter = adapter
                                adapter!!.setNewData(t)
                                adapter!!.setOnItemClickListener { adapter, view, position ->
                                    val intent = Intent(context, LQDetailActivity::class.java)
                                    startActivity(intent)
                                }
                            }

                            override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                            }

                            override fun onFail(e: ApiError) {
                            }

                        })
            }
            1 -> {
                network.getApi(Api::class.java).getLQzhuajia()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : ApiBaseResponse<List<LanqZhuModule>>(activity!!){
                            override fun onSuccess(t: List<LanqZhuModule>?) {
                                var gridLayoutManager = GridLayoutManager(context,2)
                                rv_gz.layoutManager = gridLayoutManager
                                basadapter = LanZAdpter()
                                rv_gz.adapter = basadapter
                                basadapter!!.setNewData(t)
                                basadapter!!.setOnItemClickListener { adapter, view, position ->
                                    val intent = Intent(context, LQDetailActivity::class.java)
                                    startActivity(intent)
                                }
                            }

                            override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                            }

                            override fun onFail(e: ApiError) {
                            }

                        })
            }
        }
    }

    inner class LanZAdpter : BaseQuickAdapter<LanqZhuModule,BaseViewHolder>(R.layout.item_ba_gaunhzu,basdata) {
        override fun convert(helper: BaseViewHolder?, item: LanqZhuModule?) {
            Picasso.with(context).load(item!!.zjurl).transform(CircleTransform()).into(helper!!.getView(R.id.iv_bas) as ImageView)
            helper.setText(R.id.tv_bas_name,item.zjname)
            helper.setText(R.id.tv_bas_tags,item.biaoshi)
            helper.setText(R.id.tv_bas_mz,item.mingzhong)
            helper.setText(R.id.tv_tab_one,item.tablist!![0].history)
            helper.setText(R.id.tv_tab_two,item.tablist!![1].history)
            helper.getView<TextView>(R.id.tv_gz_zhujia).setOnClickListener {
                var loginid = SPUtils.getInstance().getInt("loginid")
                if (loginid != -1) {
                    helper.getView<TextView>(R.id.tv_gz_zhujia).text = "已关注"
                    helper.getView<TextView>(R.id.tv_gz_zhujia).setBackgroundResource(R.drawable.shape_matcher_gray)
                } else {
                    var intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    inner class GZAdapter : BaseQuickAdapter<GuanZModule,BaseViewHolder>(R.layout.item_gz,data) {
        override fun convert(helper: BaseViewHolder?, item: GuanZModule?) {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = RecyclerView.HORIZONTAL
            var recyclerView = helper!!.getView<RecyclerView>(R.id.rv_his)
            recyclerView.layoutManager = linearLayoutManager
            hisAdapter = HisAdapter()
            recyclerView.adapter = hisAdapter
            Picasso.with(context).load(item!!.zjurl).transform(CircleTransform()).into(helper.getView(R.id.iv_gz_url) as ImageView)
            hist = item.tablist
            hisAdapter!!.setNewData(hist)
            helper.setText(R.id.tv_gz_name,item.zjname)
            helper.setText(R.id.tv_gz_biaoshi,item.biaoshi)
            helper.setText(R.id.tv_mz,item.mingzhong)
            helper.setText(R.id.tv_gz_desc,item.desc)
            helper.setText(R.id.tv_day,item.day)
            helper.setText(R.id.tv_gz_tags,item.tags)
            helper.setText(R.id.tv_team,item.team)
            helper.setText(R.id.tv_gz_time,item.time)
            helper.setText(R.id.tv_money,item.money)
            helper.setText(R.id.tv_fb_time,item.fatime)
        }
    }

    inner class HisAdapter : BaseQuickAdapter<GuanZModule.TablistBean,BaseViewHolder>(R.layout.item_tab,hist) {
        override fun convert(helper: BaseViewHolder?, item: GuanZModule.TablistBean?) {
            helper!!.setText(R.id.tv_tab,item!!.history)
        }

    }
}