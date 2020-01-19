package com.test.sandev.ui.activity

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseActivity
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.JiFenModule
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_jifen.*
import kotlinx.android.synthetic.main.item_jf.*

class JiFenActivity : BaseActivity() {
    val net by lazy { NetWork() }
    var data : List<JiFenModule.DatasBean>? = null
    var pm : String? = null
    var nc : String? = null
    var le : String? = null
    var jf : String? = null
    var adapter : JFAdpater? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_jifen
    }

    override fun initData() {
        pm = intent.getStringExtra("pm")
        nc = intent.getStringExtra("nc")
        le = intent.getStringExtra("le")
        jf = intent.getStringExtra("jf")
        getJF()
        tv_mine_pm.text = pm
        tv_mine_name.text = nc
        tv_mine_level.text = le
        tv_mine_jf.text = jf
        var linearLayoutManager = LinearLayoutManager(this)
        rv_jf.layoutManager = linearLayoutManager
        adapter = JFAdpater()
        rv_jf.adapter = adapter
    }

    private fun getJF() {
        net.getWanApi(Api::class.java).getJFData("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :ApiBaseResponse<JiFenModule>(this){
                    override fun onSuccess(t: JiFenModule?) {
                        data = t!!.datas
                        adapter!!.setNewData(data)
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    inner class JFAdpater : BaseQuickAdapter<JiFenModule.DatasBean,BaseViewHolder>(R.layout.item_jf,data) {
        override fun convert(helper: BaseViewHolder?, item: JiFenModule.DatasBean?) {
            if (helper!!.position == 0) {
                helper.getView<TextView>(R.id.tv_item_pm).visibility = View.GONE
                helper.getView<ImageView>(R.id.iv_pm).visibility = View.VISIBLE
                Picasso.with(this@JiFenActivity).load(R.mipmap.ic_one).into(helper.getView(R.id.iv_pm) as ImageView)
            }else if (helper!!.position == 1) {
                helper.getView<TextView>(R.id.tv_item_pm).visibility = View.GONE
                helper.getView<ImageView>(R.id.iv_pm).visibility = View.VISIBLE
                Picasso.with(this@JiFenActivity).load(R.mipmap.ic_two).into(helper.getView(R.id.iv_pm) as ImageView)
            }else if (helper!!.position == 2) {
                helper.getView<TextView>(R.id.tv_item_pm).visibility = View.GONE
                helper.getView<ImageView>(R.id.iv_pm).visibility = View.VISIBLE
                Picasso.with(this@JiFenActivity).load(R.mipmap.ic_three).into(helper.getView(R.id.iv_pm) as ImageView)
            } else {
                helper.getView<TextView>(R.id.tv_item_pm).visibility = View.VISIBLE
                helper.getView<ImageView>(R.id.iv_pm).visibility = View.GONE
                helper.setText(R.id.tv_item_pm,item!!.rank.toString())
            }
            helper.setText(R.id.tv_item_nc,item!!.username)
            helper.setText(R.id.tv_item_levl,item!!.level.toString())
            helper.setText(R.id.tv_item_jf,item!!.coinCount.toString())
        }
    }
}