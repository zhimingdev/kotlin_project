package com.test.sandev.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.LQmodule
import com.test.sandev.ui.activity.BaseketActivity
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_lanqiu.*

class LanqiuFragment : BaseFragment() {

    private var date : List<LQmodule>? = null
    var adapter : LanAdapter? = null

    val network by lazy { NetWork() }

    companion object{
        fun getInstance() :LanqiuFragment{
            val fragment = LanqiuFragment()
            return fragment
        }
    }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_lanqiu,null)
        return view
    }

    override fun initSandevDate() {
        val linearLayoutManager = LinearLayoutManager(activity)
        lq_rv.layoutManager = linearLayoutManager
        adapter = LanAdapter()
        lq_rv.adapter = adapter
        getLQ()
    }

    private fun getLQ() {
        network.getApi(Api::class.java).getLQ()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<List<LQmodule>>(activity!!){
                    override fun onSuccess(t: List<LQmodule>?) {
                        date = t
                        adapter!!.setNewData(t)
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    override fun initSandevListenter() {
        srl_lq.setOnLoadMoreListener {
            it.finishLoadMore()
            it.finishLoadMoreWithNoMoreData()
        }

        srl_lq.setOnRefreshListener {
            getLQ()
            it.finishRefresh()
        }

        adapter!!.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(activity,BaseketActivity::class.java)
            intent.putExtra("hurl", date!![position].host!!.url)
            intent.putExtra("hnumber", date!![position].host!!.number)
            intent.putExtra("gurl", date!![position].guest!!.url)
            intent.putExtra("gnumber", date!![position].guest!!.number)
            intent.putExtra("type", date!![position].type)
            intent.putExtra("time", date!![position].time)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }

    inner class LanAdapter : BaseQuickAdapter<LQmodule,BaseViewHolder>(R.layout.item_lq,date) {
        override fun convert(helper: BaseViewHolder?, item: LQmodule?) {
            Glide.with(activity).load(item!!.host!!.url).into(helper!!.getView(R.id.iv_lq_host) as ImageView)
            Glide.with(activity).load(item!!.guest!!.url).into(helper!!.getView(R.id.iv_lq_guest) as ImageView)
            helper.setText(R.id.tv_lq_time,item.time)
            if (item.type == 0) {
                helper.setText(R.id.tv_lq_type,"已结束")
            }else{
                helper.setText(R.id.tv_lq_type,"未开赛")
            }
            helper.setText(R.id.tv_lq_host_name,item.host!!.name)
            helper.setText(R.id.tv_lq_guest_name,item.guest!!.name)
            helper.setText(R.id.tv_lq_one,item.host!!.one)
            helper.setText(R.id.tv_lq_two,item.host!!.two)
            helper.setText(R.id.tv_lq_three,item.host!!.three)
            helper.setText(R.id.tv_lq_four,item.host!!.four)
            helper.setText(R.id.tv_lq_guest_one,item.guest!!.one)
            helper.setText(R.id.tv_lq_guest_two,item.guest!!.two)
            helper.setText(R.id.tv_lq_guest_three,item.guest!!.three)
            helper.setText(R.id.tv_lq_guest_four,item.guest!!.four)
            helper.setText(R.id.tv_host_number,item.host!!.number)
            helper.setText(R.id.tv_guest_number,item.guest!!.number)
        }
    }
}