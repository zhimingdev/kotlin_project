package com.test.sandev.ui.activity

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseActivity
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.RecordModule
import com.test.sandev.ui.fragment.TeamDataFragment
import com.test.sandev.ui.fragment.ZrFragment
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.CircleTransform
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_match_detail.*

class MatchDetailActivity : BaseActivity() {

    val network by lazy { NetWork() }
    val titles : MutableList<String> = mutableListOf()
    private var data : RecordModule? = null
    var fragments : MutableList<Fragment> = mutableListOf()
    private var adapter : MyAdapter? = null
    private var type :Int? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_match_detail
    }

    init {
        titles.clear()
        titles.add("球员数据")
        titles.add("比赛阵容")
    }

    override fun initData() {
        var index = intent.getIntExtra("index", 0)
        type = intent.getIntExtra("type",-1)
        if (type == 0) {
            tv_detail_type.text = "已结束"
            tv_detail_type.background = resources.getDrawable(R.drawable.shape_tv_bg)
        }else{
            tv_detail_type.text = "未开赛"
            tv_detail_type.background = resources.getDrawable(R.drawable.shape_matcher_gray)
        }
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
                        data = t
                        tv_tags.text = t!!.tags
                        Picasso.with(this@MatchDetailActivity).load(t!!.hostinfo!!.hoet_image).transform(CircleTransform()).into(iv_left_team)
                        Picasso.with(this@MatchDetailActivity).load(t!!.guestinfo!!.guest_image).transform(CircleTransform()).into(iv_right_team)
                        tv_team_name.text = t!!.hostinfo!!.hoet_name
                        tv_team_name_right.text = t!!.guestinfo!!.guest_name
                        tv_left_count.text = t!!.hostinfo!!.host_count
                        tv_right_count.text = t!!.guestinfo!!.guest_count
                        fragments.clear()
                        fragments.add(TeamDataFragment.getInstance(data))
                        fragments.add(ZrFragment.getInstance(data))
                        adapter = MyAdapter()
                        vp_detail.adapter = adapter
                        tab_detail.setupWithViewPager(vp_detail)
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

    inner class MyAdapter : FragmentPagerAdapter(this.supportFragmentManager) {
        override fun getItem(position: Int): Fragment {
            return fragments!![position]
        }

        override fun getCount(): Int {
            return titles.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles!![position]
        }

    }

}