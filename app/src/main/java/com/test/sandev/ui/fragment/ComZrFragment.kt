package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.test.sandev.R
import com.test.sandev.adapter.*
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.RecordModule
import kotlinx.android.synthetic.main.fragment_comzr.*

class ComZrFragment(private var data: RecordModule?,private var type : Int) : BaseFragment() {

    companion object{
        fun getInstance(data: RecordModule?, i: Int): ComZrFragment {
            val fragment = ComZrFragment(data,i)
            return fragment
        }
    }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_comzr,null)
        return view
    }

    override fun initSandevDate() {
        when(type) {
            1 -> {
                if (data!!.hostinfo!!.hostInfo != null) {
                    ll_zr.visibility = View.VISIBLE
                    ll_empty.visibility = View.GONE
                    var hostinfoBean = data!!.hostinfo
                    tv_host.text = data!!.hostinfo!!.hoet_name

                    var gridLayoutManager = GridLayoutManager(activity, hostinfoBean!!.hostInfo!!.onelist!!.size)
                    var gridLayoutManager1 = GridLayoutManager(activity, hostinfoBean!!.hostInfo!!.twolist!!.size)
                    var gridLayoutManager2 = GridLayoutManager(activity, hostinfoBean!!.hostInfo!!.threelist!!.size)
                    var gridLayoutManager3 = GridLayoutManager(activity, hostinfoBean!!.hostInfo!!.fourlist!!.size)
                    gv_one.layoutManager = gridLayoutManager
                    gv_two.layoutManager = gridLayoutManager1
                    gv_three.layoutManager = gridLayoutManager2
                    gv_four.layoutManager = gridLayoutManager3

                    var detailAdapter: DetailAdapter = DetailAdapter(activity!!,hostinfoBean!!.hostInfo!!.onelist!!)
                    var detailAdapter1: Detail1Adapter = Detail1Adapter(activity!!, hostinfoBean!!.hostInfo!!.twolist!!)
                    var detailAdapter2: Detai2Adapter = Detai2Adapter(activity!!, hostinfoBean!!.hostInfo!!.threelist!!)
                    var detailAdapter3: Detai3Adapter = Detai3Adapter(activity!!, hostinfoBean!!.hostInfo!!.fourlist!!)

                    gv_one.adapter = detailAdapter
                    gv_two.adapter = detailAdapter1
                    gv_three.adapter = detailAdapter2
                    gv_four.adapter = detailAdapter3

                }else{
                    ll_zr.visibility = View.GONE
                    ll_empty.visibility = View.VISIBLE
                }
            }
            2 -> {
                if (data!!.guestinfo!!.guestInfo != null) {
                    ll_zr.visibility = View.VISIBLE
                    ll_empty.visibility = View.GONE

                    tv_host.text = data!!.guestinfo!!.guest_name

                    var guestinfoBean = data!!.guestinfo
                    var gridLayoutManager4 = GridLayoutManager(activity, guestinfoBean!!.guestInfo!!.onelist!!.size)
                    var gridLayoutManager5 = GridLayoutManager(activity, guestinfoBean!!.guestInfo!!.twolist!!.size)
                    var gridLayoutManager6 = GridLayoutManager(activity, guestinfoBean!!.guestInfo!!.threelist!!.size)
                    var gridLayoutManager7 = GridLayoutManager(activity, guestinfoBean!!.guestInfo!!.fourlist!!.size)

                    var detailAdapter4: DetailGuestAdapter = DetailGuestAdapter(activity!!,guestinfoBean!!.guestInfo!!.onelist!!)
                    var detailAdapter5: DetailGuest1Adapter = DetailGuest1Adapter(activity!!, guestinfoBean!!.guestInfo!!.twolist!!)
                    var detailAdapter6: DetaiGuest2Adapter = DetaiGuest2Adapter(activity!!, guestinfoBean!!.guestInfo!!.threelist!!)
                    var detailAdapter7: DetaiGiest3Adapter = DetaiGiest3Adapter(activity!!, guestinfoBean!!.guestInfo!!.fourlist!!)

                    gv_one.layoutManager = gridLayoutManager4
                    gv_two.layoutManager = gridLayoutManager5
                    gv_three.layoutManager = gridLayoutManager6
                    gv_four.layoutManager = gridLayoutManager7

                    gv_one.adapter = detailAdapter4
                    gv_two.adapter = detailAdapter5
                    gv_three.adapter = detailAdapter6
                    gv_four.adapter = detailAdapter7

                }else{
                    ll_zr.visibility = View.GONE
                    ll_empty.visibility = View.VISIBLE
                }
            }
        }
    }
}