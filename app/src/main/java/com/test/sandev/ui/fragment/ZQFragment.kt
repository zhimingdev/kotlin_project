package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.test.sandev.R
import com.test.sandev.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_zq.*
import java.util.ArrayList

class ZQFragment : BaseFragment() {

    private var fragments : MutableList<Fragment>? = mutableListOf()
    private var adapter : VBAdapter? = null
    private val items = ArrayList<String>()

    companion object{
        fun getInstance() : ZQFragment{
            val fragment = ZQFragment()
            return fragment
        }
    }

    init {
        items.add("赛程")
        items.add("集锦")
        items.add("专家")
        fragments!!.clear()
        fragments!!.add(CommonFragment.getInstanca(0))
        fragments!!.add(CommonFragment.getInstanca(1))
        fragments!!.add(GuanZhuFragment.getInstance())
    }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_zq,null)
        return view
    }

    override fun initSandevDate() {
        adapter = VBAdapter()
        vp_zq.adapter = adapter
        tab_zq.setupWithViewPager(vp_zq)
    }

    inner class VBAdapter : FragmentPagerAdapter(activity!!.supportFragmentManager){
        override fun getItem(position: Int): Fragment {
            return fragments!![position]
        }

        override fun getCount(): Int {
            return fragments!!.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return items[position]
        }

    }
}