package com.test.sandev.ui.activity

import android.content.Intent
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import kotlin.system.exitProcess
import android.view.KeyEvent
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.blankj.utilcode.util.SPUtils
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseActivity
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.MessageEvent
import com.test.sandev.module.PackModule
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.FragmentUtils
import com.test.sandev.utils.NetWork
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import com.ashokvarma.bottomnavigation.ShapeBadgeItem
import android.R.attr.colorPrimaryDark
import com.ashokvarma.bottomnavigation.TextBadgeItem
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.test.sandev.ui.fragment.*
import org.greenrobot.eventbus.ThreadMode
import kotlin.properties.Delegates


class MainActivity : BaseActivity() {
    //需要显示的fragment
    var showfragment: Fragment? = null
    //当前显示的fragment
    var currentfragment: Fragment? = null
    var lasttime: Long = 0
    val network by lazy { NetWork() }
    var lastSelectedPosition: Int = 0
    lateinit var homeFragment: HomeFragment
    lateinit var vBangFragment: VBangFragment
    lateinit var keFuFragment: KeFuFragment
    lateinit var yueDanFragment: YueDanFragment
    lateinit var findFragment: FindFragment

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        homeFragment = HomeFragment()
        vBangFragment = VBangFragment()
        findFragment = FindFragment()
        keFuFragment = KeFuFragment()
        yueDanFragment = YueDanFragment()
        EventBus.getDefault().register(this)
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_FIXED)
        // TODO 设置背景色样式
        bottom_navigation_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        bottom_navigation_bar.setBarBackgroundColor(android.R.color.white)
        var mTextBadgeItem = TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.red)
                .setText("1")
                .setHideOnSelect(false)

        bottom_navigation_bar
                .addItem(BottomNavigationItem(R.mipmap.ic_bottom_home_icon, "首页").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
                .addItem(BottomNavigationItem(R.mipmap.ic_bottom_mv_unselect, "视频").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
                .addItem(BottomNavigationItem(R.mipmap.ic_find, "发现").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
                .addItem(BottomNavigationItem(R.mipmap.tab_kefu, "客服").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
                .addItem(BottomNavigationItem(R.mipmap.ic_bottom_mvlist_unselect, "我的").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray).setBadgeItem(mTextBadgeItem))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise()
//        network.getApi(Api::class.java).getPack()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : ApiBaseResponse<PackModule>(this) {
//                    override fun onSuccess(t: PackModule?) {
//                        if (t!!.flag!!) {
//
//                        }
//                    }
//
//                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
//                    }
//
//                    override fun onFail(e: ApiError) {
//                    }
//
//                })

        switchFragment(homeFragment)
    }

    override fun initLisenter() {
        bottom_navigation_bar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                when (position) {
                    0 -> {
                        switchFragment(homeFragment)
                        SPUtils.getInstance().put("index",position)
                    }
                    1 -> {
                        switchFragment(vBangFragment)
                        SPUtils.getInstance().put("index",position)
                    }
                    2 -> {
                        if (SPUtils.getInstance().getString("logininfo").isBlank()) {
                            var index = SPUtils.getInstance().getInt("index",0)
                            when(index) {
                                0 -> {
                                    bottom_navigation_bar.selectTab(0)
                                    SPUtils.getInstance().put("index",position)
                                }
                                1 -> {
                                    bottom_navigation_bar.selectTab(1)
                                    SPUtils.getInstance().put("index",position)
                                }
                                3 -> {
                                    bottom_navigation_bar.selectTab(3)
                                    SPUtils.getInstance().put("index",position)
                                }
                                4 -> {
                                    bottom_navigation_bar.selectTab(4)
                                    SPUtils.getInstance().put("index",position)
                                }
                            }
                            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                            return
                        } else {
                            switchFragment(findFragment)
                        }
                    }
                    3 -> {
                        switchFragment(keFuFragment)
                        SPUtils.getInstance().put("index",position)
                    }
                    4 -> {
                        switchFragment(yueDanFragment)
                        SPUtils.getInstance().put("index",position)
                    }
                    else -> switchFragment(homeFragment)
                }
            }

            override fun onTabReselected(position: Int) {
            }

        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEvent(msg: MessageEvent) {
        when (msg.message) {
            "success" -> {
                bottom_navigation_bar.selectTab(2)
//                switchFragment(findFragment)
                SPUtils.getInstance().put("index",2)
            }
        }
    }

    fun switchFragment(fragment: Fragment) {
        showfragment = fragment
        var beginTransaction = supportFragmentManager.beginTransaction()
        if (currentfragment != null && showfragment != currentfragment) {
            beginTransaction.hide(currentfragment!!)
        }
        if (showfragment!!.isAdded) {
            beginTransaction.show(showfragment!!)
        } else {
            beginTransaction.add(R.id.container, showfragment!!)
        }
        beginTransaction.commitAllowingStateLoss()
        currentfragment = showfragment
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val secondTime = System.currentTimeMillis()
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentfragment is VBangFragment) {
                if (NiceVideoPlayerManager.instance().onBackPressd()) {
                    return true
                } else {
                    if (secondTime - lasttime < 2000) {
                        exitProcess(0)
                    } else {
                        ToastUtils.showShort("再按一次返回键退出")
                        lasttime = System.currentTimeMillis()
                    }
                }
            } else {
                if (secondTime - lasttime < 2000) {
                    exitProcess(0)
                } else {
                    ToastUtils.showShort("再按一次返回键退出")
                    lasttime = System.currentTimeMillis()
                }
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
