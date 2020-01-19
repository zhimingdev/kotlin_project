package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.TextModule
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_text.*

class TextFrament(private var position: String) : BaseFragment() {

    var data : MutableList<TextModule>? = mutableListOf()
    val network by lazy { NetWork() }
    var adapter : TextAdapter? = null

    companion object{
        fun getInstance(position : String) : TextFrament{
            val fragemnt = TextFrament(position)
            return fragemnt
        }
    }

    override fun initView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_text,null)
        return view
    }

    override fun initSandevDate() {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        rv_text.layoutManager = linearLayoutManager
        adapter = TextAdapter()
        rv_text.adapter = adapter
        if (position == "0") {
            ll_one.visibility = View.VISIBLE
            ll_top.visibility = View.GONE
            ll_empty.visibility = View.GONE
            getInfo()
        }else{
            ll_one.visibility = View.GONE
            ll_empty.visibility = View.VISIBLE
        }
    }

    private fun getInfo() {
        network.getApi(Api::class.java).getText()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<List<TextModule>>(activity!!){
                    override fun onSuccess(t: List<TextModule>?) {
                        if (t!!.isNotEmpty() && t!= null) {
                            adapter!!.setNewData(t)
                        }
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    inner class TextAdapter : BaseQuickAdapter<TextModule,BaseViewHolder>(R.layout.text_item,data) {
        override fun convert(helper: BaseViewHolder?, item: TextModule?) {
            helper!!.setText(R.id.tv_content,item!!.descrption)
            helper!!.setText(R.id.tv_content_time,item!!.time)
        }

    }

}