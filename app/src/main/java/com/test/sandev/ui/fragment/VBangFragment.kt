package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.test.sandev.R
import com.test.sandev.adapter.VideoAdapter
import com.test.sandev.adapter.holder.VideoViewHolder
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.VideoModule
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_vbang.*
import java.util.ArrayList

class VBangFragment : BaseFragment() {

    val network by lazy { NetWork() }
    private val items = ArrayList<String>()
    private var mHomeFragment: CommonFragment? = null
    private var mUploadFragment: CommonFragment? = null
    private var mVideoFragment: CommonFragment? = null
    private var transaction: FragmentTransaction? = null

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_vbang,null)
        return view
    }

    override fun initSandevDate() {
        items.clear()
        items.add("推荐")
        items.add("集锦")
        items.add("广场")
        for (item in items) {
            tab.addTab(tab.newTab().setText(item))
        }
        initFragmentReplace()
    }

    override fun initSandevListenter() {
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    val fragmentManager = activity!!.supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.show(mHomeFragment!!).hide(mUploadFragment!!).hide(mVideoFragment!!).commit()
                } else if (tab.position == 1){
                    val fragmentManager = activity!!.supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.show(mVideoFragment!!).hide(mHomeFragment!!).hide(mUploadFragment!!).commit()
                } else{
                    val fragmentManager = activity!!.supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.show(mUploadFragment!!).hide(mHomeFragment!!).hide(mVideoFragment!!).commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun initFragmentReplace() {
        // 获取到fragment碎片管理器
        val manager = activity!!.supportFragmentManager
        // 开启事务
        transaction = manager.beginTransaction()

        // 获取到fragment的对象
        mHomeFragment = CommonFragment.getInstanca(0)
        mVideoFragment = CommonFragment.getInstanca(1)
        mUploadFragment = CommonFragment.getInstanca(2)


        // 设置要显示的fragment 和 影藏的fragment
        transaction!!.add(R.id.fl_vb, mHomeFragment!!, "home").show(mHomeFragment!!)
        transaction!!.add(R.id.fl_vb, mVideoFragment!!, "video").hide(mVideoFragment!!)
        transaction!!.add(R.id.fl_vb, mUploadFragment!!, "upload").hide(mUploadFragment!!)

        // 提交事务
        transaction!!.commit()

    }

}