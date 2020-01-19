package com.test.sandev.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.test.sandev.R
import com.test.sandev.adapter.MatchRecordAdapter
import com.test.sandev.api.Api
import com.test.sandev.base.BaseActivity
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.MatchModule
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_hotmatcher.*

class HotMatcher :BaseActivity(){

    val network by lazy { NetWork() }

    override fun getLayoutId(): Int {
        return R.layout.activity_hotmatcher
    }

    override fun initData() {
        var linearLayoutManager = LinearLayoutManager(this)
        rv_match_record.layoutManager = linearLayoutManager
        getDataInfo()
    }

    private fun getDataInfo() {
        network.getApi(Api::class.java).getMatchRecord()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<List<MatchModule>>(this) {
                    override fun onSuccess(t: List<MatchModule>?) {
                        var adapter : MatchRecordAdapter = MatchRecordAdapter(this@HotMatcher,t)
                        rv_match_record.adapter = adapter
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    override fun initLisenter() {
        rl_match_back.setOnClickListener {
            finish()
        }
    }
}