package com.test.sandev.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.sandev.R
import com.test.sandev.adapter.MatchRecordAdapter
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.MatchModule
import com.test.sandev.module.MessageEvent
import com.test.sandev.ui.activity.LifeRecordActivity
import com.test.sandev.ui.activity.WeskitTallyActivity
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_life_record.*
import kotlinx.android.synthetic.main.fragment_find.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FindFragment : BaseFragment() {

    val network by lazy { NetWork() }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_find,null)
        EventBus.getDefault().register(this)
        return view
    }

    override fun initSandevDate() {
        var linearLayoutManager = LinearLayoutManager(context)
        rv_match_record.layoutManager = linearLayoutManager
        getDataInfo()
    }

    private fun getDataInfo() {
        network.getApi(Api::class.java).getMatchRecord()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<List<MatchModule>>(activity!!) {
                    override fun onSuccess(t: List<MatchModule>?) {
                        var adapter : MatchRecordAdapter = MatchRecordAdapter(context!!,t)
                        rv_match_record.adapter = adapter
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    override fun initSandevListenter() {
        rl_life.setOnClickListener {
            var intent = Intent(context,LifeRecordActivity::class.java)
            startActivity(intent)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEvent(msg: MessageEvent) {
        when (msg.message) {
            "success" -> {
                init()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().register(this)
    }
}