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
        items.add("精选")
        items.add("专题")
        items.add("转会")
        items.add("英超")
        items.add("西甲")
        items.add("德甲")
        items.add("欧冠")
        items.add("意甲")
        items.add("专家")
        fragments!!.clear()
        fragments!!.add(CommonFragment.getInstanca(0))
        fragments!!.add(CommonFragment.getInstanca(1))
        fragments!!.add(CommonListViewFragment.getInstanca(7))
        fragments!!.add(CommonListViewFragment.getInstanca(8))
        fragments!!.add(CommonFragment.getInstanca(2))
        fragments!!.add(CommonFragment.getInstanca(3))
        fragments!!.add(CommonFragment.getInstanca(4))
        fragments!!.add(CommonFragment.getInstanca(5))
        fragments!!.add(CommonFragment.getInstanca(6))
        fragments!!.add(GuanZhuFragment.getInstance(0))
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