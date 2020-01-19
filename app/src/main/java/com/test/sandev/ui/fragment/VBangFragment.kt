package com.test.sandev.ui.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.test.sandev.R
import com.test.sandev.base.BaseFragment
import com.test.sandev.utils.NetWork
import kotlinx.android.synthetic.main.fragment_vbang.*

class VBangFragment : BaseFragment() {

    val network by lazy { NetWork() }
    private var fragments : MutableList<Fragment>? = mutableListOf()
    private var adapter : VBAdapter? = null

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_vbang,null)
        return view
    }

    override fun initSandevDate() {
        initFragmentReplace()
    }

    override fun initSandevListenter() {
        fl_vb.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            @SuppressLint("MissingSuperCall")
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        fl_vb.currentItem = 0
                        tv_one.setTextColor(resources.getColor(R.color.white))
                        tv_one.background = resources.getDrawable(R.drawable.l_bg7)
                        tv_two.setTextColor(resources.getColor(R.color.colorPrimary))
                        tv_two.background = resources.getDrawable(R.drawable.l_bg8)
                    }
                    1 -> {
                        fl_vb.currentItem = 1
                        tv_two.setTextColor(resources.getColor(R.color.white))
                        tv_two.background = resources.getDrawable(R.drawable.l_bg8_normal)
                        tv_one.setTextColor(resources.getColor(R.color.colorPrimary))
                        tv_one.background = resources.getDrawable(R.drawable.l_bg_nomal)
                    }
                }
            }

        })

        tv_one.setOnClickListener {
            fl_vb.currentItem = 0
            tv_one.setTextColor(resources.getColor(R.color.white))
            tv_one.background = resources.getDrawable(R.drawable.l_bg7)
            tv_two.setTextColor(resources.getColor(R.color.colorPrimary))
            tv_two.background = resources.getDrawable(R.drawable.l_bg8)
        }

        tv_two.setOnClickListener {
            fl_vb.currentItem = 1
            tv_two.setTextColor(resources.getColor(R.color.white))
            tv_two.background = resources.getDrawable(R.drawable.l_bg8_normal)
            tv_one.setTextColor(resources.getColor(R.color.colorPrimary))
            tv_one.background = resources.getDrawable(R.drawable.l_bg_nomal)
        }
    }

    private fun initFragmentReplace() {
        fragments!!.clear()
        fragments!!.add(ZQFragment.getInstance())
        fragments!!.add(LQFragment.getInstance())
        adapter = VBAdapter()
        fl_vb.adapter = adapter
        fl_vb.currentItem = 0
    }

    inner class VBAdapter : FragmentPagerAdapter(activity!!.supportFragmentManager){
        override fun getItem(position: Int): Fragment {
            return fragments!![position]
        }

        override fun getCount(): Int {
            return fragments!!.size
        }
    }

}