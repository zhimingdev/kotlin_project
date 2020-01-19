package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.ZJmodule
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_zj.*

class ZJFragment(private var position: Int) : BaseFragment() {

    val network by lazy { NetWork() }

    companion object{
        fun getInstanca(type : Int) : ZJFragment {
            return ZJFragment(type)
        }
    }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_zj,null)
        return view
    }

    override fun initSandevDate() {
        var map : MutableMap<String,Int> = mutableMapOf()
        map["id"] = position
        network.getApi(Api::class.java).getzj(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<ZJmodule>(activity!!) {
                    override fun onSuccess(t: ZJmodule?) {
                        tv_h_name.text = t!!.host!!.name
                        tv_h_pm.text = t!!.host!!.paiming
                        tv_h_sf.text = t!!.host!!.zj
                        tv_h_q.text = t!!.host!!.qs
                        tv_g_name.text = t!!.guest!!.name
                        tv_g_pm.text = t!!.guest!!.paiming
                        tv_g_sf.text = t!!.guest!!.zj
                        tv_g_q.text = t!!.guest!!.qs
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }
                })
    }
}