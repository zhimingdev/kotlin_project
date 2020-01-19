package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.test.sandev.R
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.RecordModule
import kotlinx.android.synthetic.main.fragment_rz.*

class ZrFragment(private var data: RecordModule?) : BaseFragment() {

    val titles : MutableList<String> = mutableListOf()
    private var info : RecordModule? = null
    var fragments : MutableList<Fragment> = mutableListOf()
    var adapter : ZrAdapter? = null

    companion object{
        fun getInstance(data: RecordModule?): ZrFragment {
            val fragment = ZrFragment(data)
            return fragment
        }
    }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_rz,null)
        return view
    }

    override fun initSandevDate() {
        titles.clear()
        titles.add(data!!.hostinfo!!.hoet_name!!)
        titles.add(data!!.guestinfo!!.guest_name!!)
        fragments.add(ComZrFragment.getInstance(data,1))
        fragments.add(ComZrFragment.getInstance(data,2))
        adapter = ZrAdapter()
        vp_zr.adapter = adapter
        tab_zr.setupWithViewPager(vp_zr)
    }

    inner class ZrAdapter : FragmentPagerAdapter(activity!!.supportFragmentManager) {
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