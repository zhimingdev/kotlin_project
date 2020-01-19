package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.sandev.R
import com.test.sandev.adapter.InfoItem
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.RecordModule
import kotlinx.android.synthetic.main.fragment_data.*

class TeamDataFragment(private var data: RecordModule?) : BaseFragment() {

    companion object{
        fun getInstance(data: RecordModule?): TeamDataFragment {
            val fragment = TeamDataFragment(data)
            return fragment
        }
    }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_data,null)
        return view
    }

    override fun initSandevDate() {
        tv_host_zr.text = data!!.hostinfo!!.hoet_name
        tv_guset_zr.text = data!!.guestinfo!!.guest_name

        val linearLayoutManager = LinearLayoutManager(activity)
        rc_info.layoutManager = linearLayoutManager
        if (data!!.playinfo!! != null && data!!.playinfo!!.isNotEmpty()) {
            ll_playinfo.visibility = View.VISIBLE
            ll_empty.visibility = View.GONE
            var itemadapter = InfoItem(activity!!,data!!.playinfo!!)
            rc_info.adapter = itemadapter
        }else{
            ll_playinfo.visibility = View.GONE
            ll_empty.visibility = View.VISIBLE
        }
    }

}