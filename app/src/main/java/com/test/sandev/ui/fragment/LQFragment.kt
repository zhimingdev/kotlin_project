package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.test.sandev.R
import com.test.sandev.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_lq.*

class LQFragment : BaseFragment() {
    private var titles : MutableList<String> = mutableListOf()
    var fragments : MutableList<Fragment> = mutableListOf()
    var adapter : LanqAdapter? = null

    companion object{
        fun getInstance() : LQFragment{
            val fragment = LQFragment()
            return fragment
        }
    }

    init {
        titles.clear()
        titles.add("赛事")
        titles.add("关注")
    }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_lq,null)
        return view
    }

    override fun initSandevDate() {
        fragments.clear()
        fragments.add(LanqiuFragment.getInstance())
        fragments.add(AliveFragment.getInstance())
        adapter = LanqAdapter()
        vp_lq.adapter = adapter
        tab_lq.setupWithViewPager(vp_lq)
    }

    inner class LanqAdapter : FragmentPagerAdapter(activity!!.supportFragmentManager){
        override fun getItem(position: Int): Fragment {
            return fragments!![position]
        }

        override fun getCount(): Int {
            return fragments!!.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

    }
}