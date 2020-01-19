package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.CircleCrop
import com.test.sandev.utils.CircleTransform
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.fragment_gaunzhu.*
import kotlinx.android.synthetic.main.item_gz.*

class GuanZhuFragment : BaseFragment(){

    val network by lazy { NetWork() }
    var data : List<GuanZModule>? = null
    var hist : List<GuanZModule.TablistBean>? = null
    var hisAdapter : HisAdapter? = null
    var adapter : GZAdapter? = null

    companion object{
        fun getInstance() :GuanZhuFragment{
            val fragemnt = GuanZhuFragment()
            return fragemnt
        }
    }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_gaunzhu,null)
        return view
    }

    override fun initSandevDate() {
        var linearLayoutManager = LinearLayoutManager(context)
        rv_gz.layoutManager = linearLayoutManager
        adapter = GZAdapter()
        rv_gz.adapter = adapter
        getGuanzhu()
    }

    private fun getGuanzhu() {
        network.getApi(Api::class.java).getZUguanzhu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<List<GuanZModule>>(activity!!){
                    override fun onSuccess(t: List<GuanZModule>?) {
                        adapter!!.setNewData(t)
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })

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