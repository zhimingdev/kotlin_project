package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.InfoModule
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_text.*

class InfoFragment(private var host:String,private var guest : String,private var position: String) : BaseFragment() {

    var adapter : InfoFragment.InfoAdapter? = null
    var data : MutableList<InfoModule>? = mutableListOf()
    val network by lazy { NetWork() }

    companion object{
        fun getInstance(host:String,guest : String,position : String) : InfoFragment{
            val fragemnt = InfoFragment(host,guest,position)
            return fragemnt
        }
    }

    override fun initView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_text,null)
        return view
    }

    override fun initSandevDate() {
        tv_one.text = host
        tv_two.text = guest
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        rv_text.layoutManager = linearLayoutManager
        adapter = InfoAdapter()
        rv_text.adapter = adapter
        if (position == "0" || position == "1" ||position == "2") {
            rv_text.visibility = View.VISIBLE
            ll_empty.visibility = View.GONE
            getInfo()
        }else{
            ll_one.visibility = View.GONE
            ll_empty.visibility = View.VISIBLE
        }
    }

    private fun getInfo() {
        val map : MutableMap<String, Int> = mutableMapOf()
        map["id"] = position.toInt()
        network.getApi(Api::class.java).getInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<List<InfoModule>>(activity!!) {
                    override fun onSuccess(t: List<InfoModule>?) {
                        if (t!!.isNotEmpty() && t != null) {
                            adapter!!.setNewData(t)
                        }
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    inner class InfoAdapter : BaseQuickAdapter<InfoModule,BaseViewHolder>(R.layout.data_item,data){
        override fun convert(helper: BaseViewHolder?, item: InfoModule?) {
            helper!!.setText(R.id.tv_host_number,item!!.host)
            helper.getView<ProgressBar>(R.id.pb_host).progress = item.host!!.toInt()
            helper!!.setText(R.id.tv_item_type,item!!.type)
            helper!!.setText(R.id.tv_guest_number,item!!.guest)
            helper.getView<ProgressBar>(R.id.pb_guest).progress = item.guest!!.toInt()
        }
    }
}