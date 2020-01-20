package com.test.sandev.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseActivity
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.DetailModule
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detai_lq.*


class LQDetailActivity : BaseActivity() {

    var data : List<DetailModule>? = null
    var adapter : MyAdapter? = null
    val network by lazy { NetWork() }
    var records : List<DetailModule.RedordsBean>? = null
    var recoAdpter : RecodAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_detai_lq
    }

    override fun initData() {
        val linearLayoutManager = LinearLayoutManager(this)
        rv_bas_deta.layoutManager = linearLayoutManager
        adapter = MyAdapter()
        rv_bas_deta.adapter = adapter
        getpeopleDetail()
    }

    private fun getpeopleDetail() {
        network.getApi(Api::class.java).getDetailpeople()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :ApiBaseResponse<List<DetailModule>>(this){
                    override fun onSuccess(t: List<DetailModule>?) {
                        adapter!!.setNewData(t)
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })

    }

    override fun initLisenter() {
        rl_bas_deta_back.setOnClickListener {
            finish()
        }
    }

    inner class MyAdapter : BaseQuickAdapter<DetailModule,BaseViewHolder>(R.layout.zj_detail,data) {
        override fun convert(helper: BaseViewHolder?, item: DetailModule?) {
            helper!!.setText(R.id.tv_deta_tags,item!!.tags)
            helper.setText(R.id.tv_deta_title,item.titles)
            helper.setText(R.id.tv_deta_time,item.cjsj)
            var recyclerView = helper.getView<RecyclerView>(R.id.rv_deta)
            val linearLayoutManager = LinearLayoutManager(this@LQDetailActivity)
            recyclerView.layoutManager = linearLayoutManager
            records = item.redords
            recoAdpter = RecodAdapter()
            recyclerView.adapter = recoAdpter
            recoAdpter!!.setNewData(records)
        }
    }

    inner class RecodAdapter : BaseQuickAdapter<DetailModule.RedordsBean,BaseViewHolder>(R.layout.item_reco,records) {
        override fun convert(helper: BaseViewHolder?, item: DetailModule.RedordsBean?) {
            helper!!.setText(R.id.tv_left,item!!.team)
            helper!!.setText(R.id.tv_right,item!!.time)
        }

    }
}