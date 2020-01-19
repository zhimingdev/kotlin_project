package com.test.sandev.ui.activity

import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.bumptech.glide.Glide
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import com.test.sandev.ui.fragment.AliveFragment
import com.test.sandev.ui.fragment.ZJFragment
import kotlinx.android.synthetic.main.actiivty_basketbar.*

class BaseketActivity : BaseActivity() {
    var fragments : MutableList<Fragment> = mutableListOf()
    private var titles :MutableList<String> = mutableListOf()
    var adapter : BasAdapter? = null

    init {
        titles.clear()
        titles.add("战绩")
        titles.add("分析")
        titles.add("实况")
    }

    override fun getLayoutId(): Int {
        return R.layout.actiivty_basketbar
    }

    override fun initData() {
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView: View = window.decorView
            val option: Int = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }
        var hurl = intent.getStringExtra("hurl")
        var hnumber = intent.getStringExtra("hnumber")
        var gurl = intent.getStringExtra("gurl")
        var gnumber = intent.getStringExtra("gnumber")
        var type = intent.getIntExtra("type", -1)
        var time = intent.getStringExtra("time")
        var position = intent.getIntExtra("position",-1)
        tv_bs_time.text = time
        if (type == 0) {
            tv_ba_type.text = "已结束"
        }else{
            tv_ba_type.text = "未开赛"
        }
        tv_bas_host_number.text = hnumber
        tv_bas_guest_number.text = gnumber

        Glide.with(this).load(hurl).into(iv_bas_host_number)
        Glide.with(this).load(gurl).into(iv_bas_guest_number)
        fragments.clear()
        fragments.add(ZJFragment.getInstanca(position))
        fragments.add(AliveFragment.getInstance())
        fragments.add(AliveFragment.getInstance())
        adapter = BasAdapter()
        bas_detail_vp.adapter = adapter
        tab_bas_detail.setupWithViewPager(bas_detail_vp)
    }

    inner class BasAdapter : FragmentPagerAdapter(this.supportFragmentManager){
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return titles.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}