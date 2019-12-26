package com.test.sandev.ui.activity

import android.annotation.SuppressLint
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.adapter.*
import com.test.sandev.api.Api
import com.test.sandev.base.BaseActivity
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.RecordModule
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.CircleCrop
import com.test.sandev.utils.CircleTransform
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_match_detail.*

class MatchDetailActivity : BaseActivity() {

    val network by lazy { NetWork() }

    override fun getLayoutId(): Int {
        return R.layout.activity_match_detail
    }

    override fun initData() {
        var index = intent.getIntExtra("index", 0)
        getData(index)
    }

    private fun getData(position : Int) {
        var map : MutableMap<String,Int> = mutableMapOf()
        map["id"] = position
        network.getApi(Api::class.java).getRecordDetail(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<RecordModule>(this){
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(t: RecordModule?) {
                        tv_tags.text = t!!.tags
                        Picasso.with(this@MatchDetailActivity).load(t!!.hostinfo!!.hoet_image).transform(CircleTransform()).into(iv_left_team)
                        Picasso.with(this@MatchDetailActivity).load(t!!.guestinfo!!.guest_image).transform(CircleTransform()).into(iv_right_team)
                        tv_team_name.text = t!!.hostinfo!!.hoet_name
                        tv_team_name_right.text = t!!.guestinfo!!.guest_name
                        tv_left_count.text = t!!.hostinfo!!.host_count
                        tv_right_count.text = t!!.guestinfo!!.guest_count
                        tv_host.text = t!!.hostinfo!!.hoet_name+"阵容"
                        tv_guest.text = t!!.guestinfo!!.guest_name+"阵容"
                        updateUi(t)
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

    private fun updateUi(recordModule: RecordModule?) {
        var hostinfoBean = recordModule!!.hostinfo
        var guestinfoBean = recordModule!!.guestinfo
        var gridLayoutManager = GridLayoutManager(this, hostinfoBean!!.hostInfo!!.onelist!!.size)
        var gridLayoutManager1 = GridLayoutManager(this, hostinfoBean!!.hostInfo!!.twolist!!.size)
        var gridLayoutManager2 = GridLayoutManager(this, hostinfoBean!!.hostInfo!!.threelist!!.size)
        var gridLayoutManager3 = GridLayoutManager(this, hostinfoBean!!.hostInfo!!.fourlist!!.size)
        gv_one.layoutManager = gridLayoutManager
        gv_two.layoutManager = gridLayoutManager1
        gv_three.layoutManager = gridLayoutManager2
        gv_four.layoutManager = gridLayoutManager3

        var gridLayoutManager4 = GridLayoutManager(this, guestinfoBean!!.guestInfo!!.onelist!!.size)
        var gridLayoutManager5 = GridLayoutManager(this, guestinfoBean!!.guestInfo!!.twolist!!.size)
        var gridLayoutManager6 = GridLayoutManager(this, guestinfoBean!!.guestInfo!!.threelist!!.size)
        var gridLayoutManager7 = GridLayoutManager(this, guestinfoBean!!.guestInfo!!.fourlist!!.size)
        gv_guset_one.layoutManager = gridLayoutManager4
        gv_guset_two.layoutManager = gridLayoutManager5
        gv_guset_three.layoutManager = gridLayoutManager6
        gv_guset_four.layoutManager = gridLayoutManager7

        var detailAdapter: DetailAdapter = DetailAdapter(this,hostinfoBean!!.hostInfo!!.onelist!!)
        var detailAdapter1: Detail1Adapter  = Detail1Adapter(this, hostinfoBean!!.hostInfo!!.twolist!!)
        var detailAdapter2: Detai2Adapter = Detai2Adapter(this, hostinfoBean!!.hostInfo!!.threelist!!)
        var detailAdapter3: Detai3Adapter = Detai3Adapter(this, hostinfoBean!!.hostInfo!!.fourlist!!)

        var detailAdapter4: DetailGuestAdapter = DetailGuestAdapter(this,guestinfoBean!!.guestInfo!!.onelist!!)
        var detailAdapter5: DetailGuest1Adapter  = DetailGuest1Adapter(this, guestinfoBean!!.guestInfo!!.twolist!!)
        var detailAdapter6: DetaiGuest2Adapter = DetaiGuest2Adapter(this, guestinfoBean!!.guestInfo!!.threelist!!)
        var detailAdapter7: DetaiGiest3Adapter = DetaiGiest3Adapter(this, guestinfoBean!!.guestInfo!!.fourlist!!)

        gv_one.adapter = detailAdapter
        gv_two.adapter = detailAdapter1
        gv_three.adapter = detailAdapter2
        gv_four.adapter = detailAdapter3

        gv_guset_one.adapter = detailAdapter4
        gv_guset_two.adapter = detailAdapter5
        gv_guset_three.adapter = detailAdapter6
        gv_guset_four.adapter = detailAdapter7

        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        rc_info.layoutManager = linearLayoutManager
        var itemadapter = InfoItem(this,recordModule!!.playinfo!!)
        rc_info.adapter = itemadapter
    }
}