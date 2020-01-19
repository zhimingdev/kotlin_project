package com.test.sandev.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.sandev.R
import com.test.sandev.adapter.SqAdapter
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.SqModule
import com.test.sandev.ui.activity.AddInfoActivity
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_quan.*


class KeFuFragment : BaseFragment() {

    val netWork by lazy { NetWork() }
    var sqAdapter : SqAdapter? = null

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_quan,null)
        return view
    }

    override fun initSandevDate() {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        rv_quan!!.layoutManager = linearLayoutManager
        getQuan()
    }

    private fun getQuan() {
        netWork.getApi(Api::class.java).getSqData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<List<SqModule>>(activity!!) {
                    override fun onSuccess(t: List<SqModule>?) {
                        if (sqAdapter == null) {
                            sqAdapter = SqAdapter(context,t!!)
                        }
                        rv_quan.adapter = sqAdapter
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }
                })
    }

    override fun initSandevListenter() {
        tv_add.setOnClickListener {
            val intent = Intent(activity,AddInfoActivity::class.java)
            startActivity(intent)
        }
        srl_kf.setOnRefreshListener {
            getQuan()
            it.finishRefresh()
        }
        srl_kf.setOnLoadMoreListener {
            it.finishLoadMore()
            it.finishLoadMoreWithNoMoreData()
        }
    }
}