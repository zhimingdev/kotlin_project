package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.TeamModule
import com.test.sandev.module.TextModule
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_team_info.*

class TeamInfoFragment(private var host: String, private var guest: String, private var position: String) : BaseFragment() {

    val network by lazy { NetWork() }


    companion object {
        fun getInstance(host: String, guest: String, position: String): TeamInfoFragment {
            val fragment = TeamInfoFragment(host, guest, position)
            return fragment
        }
    }

    override fun initView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_team_info, null)
        return view
    }

    override fun initSandevDate() {
        getTeamInfo()
    }

    private fun getTeamInfo() {
        val map: MutableMap<String, Int> = mutableMapOf()
        map["id"] = position.toInt()
        network.getApi(Api::class.java).getTeam(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<TeamModule>(activity!!) {
                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                    override fun onSuccess(t: TeamModule?) {
                        updateUI(t)
                    }
                })
    }

    private fun updateUI(t: TeamModule?) {
        tv_team_detail.text = host
        tv_host_saishi.text = host
        tv_ying.text = t!!.host!!.win
        tv_shu.text = t!!.host!!.lose
        tv_ping.text = t!!.host!!.ping
        tv_paiming.text = t!!.host!!.paiming
        tv_jingqiu.text = t!!.host!!.jinqiu
        tv_hostsaishi_time.text = t!!.host!!.matcher!![0].time
        tv_hostsaishi_liansai.text = t!!.host!!.matcher!![0].tags
        tv_hostsaishi_duizheng.text = t!!.host!!.matcher!![0].team
        tv_hostsaishi_bifen.text = t!!.host!!.matcher!![0].score
        tv_hostsaishi_type.text = t!!.host!!.matcher!![0].type
        tv_hostsaishi_two_time.text = t!!.host!!.matcher!![1].time
        tv_hostsaishi_two_liansai.text = t!!.host!!.matcher!![1].tags
        tv_hostsaishi_two_duizheng.text = t!!.host!!.matcher!![1].team
        tv_hostsaishi_two_bifen.text = t!!.host!!.matcher!![1].score
        tv_hostsaishi_two_type.text = t!!.host!!.matcher!![1].type

        tv_team_guest.text = guest
        tv_guest_saishi.text = guest
        tv_guest_ying.text = t!!.guest!!.win
        tv_guest_shu.text = t!!.guest!!.lose
        tv_guest_ping.text = t!!.guest!!.ping
        tv_guest_paiming.text = t!!.guest!!.paiming
        tv_guest_jingqiu.text = t!!.guest!!.jinqiu
        tv_guestsaishi_time.text = t!!.guest!!.matcher!![0].time
        tv_guestsaishi_liansai.text = t!!.guest!!.matcher!![0].tags
        tv_guestsaishi_duizheng.text = t!!.guest!!.matcher!![0].team
        tv_guestsaishi_bifen.text = t!!.guest!!.matcher!![0].score
        tv_guestsaishi_type.text = t!!.guest!!.matcher!![0].type
        tv_guestsaishi_two_time.text = t!!.guest!!.matcher!![1].time
        tv_guestsaishi_two_liansai.text = t!!.guest!!.matcher!![1].tags
        tv_guestsaishi_two_duizheng.text = t!!.guest!!.matcher!![1].team
        tv_guestsaishi_two_bifen.text = t!!.guest!!.matcher!![1].score
        tv_guestsaishi_two_type.text = t!!.guest!!.matcher!![1].type
    }
}