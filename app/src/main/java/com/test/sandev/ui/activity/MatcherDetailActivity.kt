package com.test.sandev.ui.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import com.test.sandev.ui.fragment.AliveFragment
import com.test.sandev.ui.fragment.InfoFragment
import com.test.sandev.ui.fragment.TeamInfoFragment
import com.test.sandev.ui.fragment.TextFrament
import com.test.sandev.utils.CircleTransform
import kotlinx.android.synthetic.main.activity_detail.*


class MatcherDetailActivity : BaseActivity() {

    private var fragments : MutableList<Fragment> = mutableListOf()
    private val titles = arrayOf("球队信息", "数据", "文字直播","直播")
    var adapter : MaDetailAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun initData() {

        var host = intent.getStringExtra("host")
        var hostname = intent.getStringExtra("hostname")
        var guest = intent.getStringExtra("guest")
        var guestname = intent.getStringExtra("guestname")
        var position = intent.getStringExtra("position")
        var type = intent.getStringExtra("type")
        var time = intent.getStringExtra("time")
        var bifen = intent.getStringExtra("bifen")
        tv_bifen.text = bifen
        tv_detail_type.text = type
        tv_detail_title.text = time
        Picasso.with(this).load(host)
                .transform(CircleTransform())
                .into(iv_hot_host)
        tv_hot_host.text = hostname
        Picasso.with(this).load(guest)
                .transform(CircleTransform())
                .into(iv_hot_guest)
        tv_hot_guest.text = guestname
        fragments.add(TeamInfoFragment.getInstance(hostname,guestname,position))
        fragments.add(InfoFragment.getInstance(hostname,guestname,position))
        fragments.add(TextFrament.getInstance(position))
        fragments.add(AliveFragment.getInstance())
        adapter = MaDetailAdapter()
        vp_content.adapter = adapter
        tl_tabs.setupWithViewPager(vp_content)
    }

    override fun initLisenter() {
        rl_detail_back.setOnClickListener {
            finish()
        }
    }

    inner class MaDetailAdapter : FragmentPagerAdapter(this.supportFragmentManager){
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

    }
}