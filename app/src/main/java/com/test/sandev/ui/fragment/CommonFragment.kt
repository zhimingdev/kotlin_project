package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.test.sandev.R
import com.test.sandev.adapter.HomeAdapter
import com.test.sandev.adapter.SqAdapter
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.HomeBean
import com.test.sandev.module.SqModule
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_common.*

class CommonFragment(private var number : Int) : BaseFragment() {

    val netWork by lazy { NetWork() }
    var homeAdapter : HomeAdapter? = null
    var sqAdapter : SqAdapter? = null

    companion object {
        fun getInstanca(type : Int) : CommonFragment {
            return CommonFragment(type)
        }
    }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_common,null)
        return view
    }

    override fun initSandevDate() {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView!!.layoutManager = linearLayoutManager
        loadData()
    }

    private fun loadData() {
        if (number == 0) {
            netWork.getApi(Api::class.java).getHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<HomeBean>(activity!!) {
                    override fun onSuccess(t: HomeBean?) {
                        if (homeAdapter == null) {
                            homeAdapter = HomeAdapter(context,t!!.datas)
                        }
                        recyclerView.adapter = homeAdapter
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })

//            netWork.getApi(Api::class.java).getAaData()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(object : ApiBaseResponse<HomeBean>(activity!!) {
//                        override fun onSuccess(t: HomeBean?) {
//                            if (homeAdapter == null) {
//                                homeAdapter = HomeAdapter(context,t!!.datas)
//                            }
//                            recyclerView.adapter = homeAdapter
//                        }
//
//                        override fun onCodeError(tBaseReponse: BaseResponse<*>) {
//                        }
//
//                        override fun onFail(e: ApiError) {
//                        }
//
//                    })
        }else{
            netWork.getApi(Api::class.java).getSqData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : ApiBaseResponse<List<SqModule>>(activity!!) {
                        override fun onSuccess(t: List<SqModule>?) {
                            if (sqAdapter == null) {
                                sqAdapter = SqAdapter(context,t!!)
                            }
                            recyclerView.adapter = sqAdapter
                        }

                        override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                        }

                        override fun onFail(e: ApiError) {
                        }
                    })
        }
    }
}