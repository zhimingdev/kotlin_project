package com.test.sandev.ui.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import com.test.sandev.ui.fragment.AliveFragment
import kotlinx.android.synthetic.main.activity_yuce.*

class YuCeActivity : BaseActivity() {

    var titles : MutableList<String> = mutableListOf()
    var faragments : MutableList<Fragment> = mutableListOf()
    var adapter :YCAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_yuce
    }

    init {
        titles.clear()
        titles.add("胜负平")
        titles.add("大小球")
        titles.add("让球")
        titles.add("总进球")
        titles.add("比分")
        titles.add("上半进球")
        titles.add("串关")
    }

    override fun initData() {
        faragments.clear()
        faragments.add(AliveFragment.getInstance())
        faragments.add(AliveFragment.getInstance())
        faragments.add(AliveFragment.getInstance())
        faragments.add(AliveFragment.getInstance())
        faragments.add(AliveFragment.getInstance())
        faragments.add(AliveFragment.getInstance())
        faragments.add(AliveFragment.getInstance())
        adapter = YCAdapter()
        vp_yuce.adapter = adapter
        tab_yuce.setupWithViewPager(vp_yuce)
    }

    override fun initLisenter() {
        rl_yuce_back.setOnClickListener {
            finish()
        }
    }

    inner class YCAdapter : FragmentPagerAdapter(this.supportFragmentManager) {
        override fun getItem(position: Int): Fragment {
            return faragments!![position]
        }

        override fun getCount(): Int {
            return titles.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles!![position]
        }

    }
}