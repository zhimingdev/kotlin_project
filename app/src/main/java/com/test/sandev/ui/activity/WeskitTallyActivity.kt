package com.test.sandev.ui.activity

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import com.test.sandev.ui.fragment.WeskitTallyFragment
import kotlinx.android.synthetic.main.activity_weskit_tally.*
import java.util.ArrayList

class WeskitTallyActivity : BaseActivity() {

    private val tabArray = arrayOf("支出", "收入")
    private var mWeskitOutFragment: WeskitTallyFragment? = null
    private var mWeskitInFragment: WeskitTallyFragment? = null
    internal var mFragments: MutableList<Fragment> = ArrayList<Fragment>()

    override fun getLayoutId(): Int {
        return R.layout.activity_weskit_tally
    }

    override fun initData() {
        mWeskitOutFragment = WeskitTallyFragment.getInstance(WeskitTallyFragment.TYPE_PAYOUT)
        mWeskitInFragment = WeskitTallyFragment.getInstance(WeskitTallyFragment.TYPE_PAYIN)
        mFragments.add(mWeskitOutFragment!!)
        mFragments.add(mWeskitInFragment!!)
        weskit_tab.setSelectedTabIndicatorColor(resources.getColor(R.color.weskit_tab_background))
        weskit_vp.adapter = MyAdapter(supportFragmentManager)
        weskit_tab.setupWithViewPager(weskit_vp)
    }

    inner class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return mFragments.size
        }


        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        @Nullable
        override fun getPageTitle(position: Int): CharSequence {
            return tabArray[position]
        }
    }
}