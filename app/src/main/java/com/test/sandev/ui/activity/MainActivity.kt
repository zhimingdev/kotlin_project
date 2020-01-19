package com.test.sandev.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.TextBadgeItem
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseActivity
import com.test.sandev.module.*
import com.test.sandev.ui.fragment.*
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_web.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.system.exitProcess


class MainActivity : BaseActivity() {
    //需要显示的fragment
    var showfragment: Fragment? = null
    //当前显示的fragment
    var currentfragment: Fragment? = null
    var lasttime: Long = 0
    var lastSelectedPosition: Int = 0
    lateinit var homeFragment: HomeFragment
    lateinit var vBangFragment: VBangFragment
    lateinit var keFuFragment: KeFuFragment
    lateinit var yueDanFragment: YueDanFragment
    lateinit var findFragment: FindFragment
    var mTextBadgeItem: TextBadgeItem? = null
    var data: PackModule? = null
    var netWork: NetWork? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        netWork = NetWork()
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
        mTextBadgeItem = TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.red)
                .setText("1")
                .setHideOnSelect(false)
//            bottom_navigation_bar
//                    .addItem(BottomNavigationItem(R.mipmap.ic_bottom_home_icon, "首页").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
//                    .addItem(BottomNavigationItem(R.mipmap.ic_bottom_mv_unselect, "视频").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
//                    .addItem(BottomNavigationItem(R.mipmap.ic_find, "发现").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
//                    .addItem(BottomNavigationItem(R.mipmap.tab_kefu, "客服").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
//                    .addItem(BottomNavigationItem(R.mipmap.ic_bottom_mvlist_unselect, "我的").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray).setBadgeItem(mTextBadgeItem))
//                    .setFirstSelectedPosition(lastSelectedPosition)
//                    .initialise()
        getShowData()
        switchFragment(homeFragment)
        var websetting = web_main.settings
        websetting.javaScriptEnabled = true
        websetting.javaScriptCanOpenWindowsAutomatically = true
        websetting.allowFileAccess = true
        websetting.displayZoomControls = true
        websetting.setSupportZoom(true)
        websetting.cacheMode = WebSettings.LOAD_NO_CACHE
        websetting.domStorageEnabled = true
        websetting.databaseEnabled = true
        web_main.webViewClient = webviewclient
        web_main.webChromeClient = webChromeClient
        showOpenStates()
    }

    private fun showOpenStates() {
        netWork!!.getNewMockApi(Api::class.java).getOpenUrl()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<OpenUrlModule>(this!!) {
                    override fun onSuccess(t: OpenUrlModule?) {
                        updateUI(t!!)
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    private fun updateUI(module: OpenUrlModule) {
        if (module.open!!) {
            web_main.visibility = View.VISIBLE
            ll_main.visibility = View.GONE
            web_main.loadUrl(module.weburl)
            web_main.setDownloadListener { url, _, _, _, _ ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        } else {
            web_main.visibility = View.GONE
            ll_main.visibility = View.VISIBLE
        }
    }

    private var webviewclient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return try {
                if (url!!.startsWith("http:") || url.startsWith("https:")) {
                    view!!.loadUrl(url)
                }
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    private val webChromeClient: WebChromeClient = object : WebChromeClient() {}

    private fun getShowData() {
        netWork!!.getApi(Api::class.java).getPack()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<PackModule>(this!!) {
                    override fun onSuccess(t: PackModule?) {
                        data = t!!
                        if (t!!.flag!!) {
                            bottom_navigation_bar
                                    .addItem(BottomNavigationItem(R.mipmap.ic_bottom_home_icon, "首页").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
                                    .addItem(BottomNavigationItem(R.mipmap.ic_bottom_mv_unselect, "赛程").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
                                    .addItem(BottomNavigationItem(R.mipmap.ic_find, "场馆").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
                                    .addItem(BottomNavigationItem(R.mipmap.tab_kefu, "客服").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
                                    .addItem(BottomNavigationItem(R.mipmap.ic_bottom_mvlist_unselect, "我的").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray).setBadgeItem(mTextBadgeItem))
                                    .setFirstSelectedPosition(lastSelectedPosition)
                                    .initialise()
                        } else {
                            bottom_navigation_bar
                                    .addItem(BottomNavigationItem(R.mipmap.ic_bottom_home_icon, "首页").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
                                    .addItem(BottomNavigationItem(R.mipmap.ic_bottom_mv_unselect, "赛程").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
                                    .addItem(BottomNavigationItem(R.mipmap.ic_find, "场馆").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
                                    .addItem(BottomNavigationItem(R.mipmap.ic_quan, "圈子").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray))
                                    .addItem(BottomNavigationItem(R.mipmap.ic_bottom_mvlist_unselect, "我的").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(android.R.color.darker_gray).setBadgeItem(mTextBadgeItem))
                                    .setFirstSelectedPosition(lastSelectedPosition)
                                    .initialise()
                        }
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })


        //        netWork.getApi(Api.class).getVV()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ApiBaseResponse<List<BannerBean>>(getActivity()) {
//                    @Override
//                    public void onFail(@NotNull ApiError e) {}
//
//                    @Override
//                    public void onCodeError(@NotNull BaseResponse<?> tBaseReponse) {}
//
//                    @Override
//                    public void onSuccess(@Nullable List<BannerBean> bannerBeans) {
//                        list = bannerBeans;
//                        bannerView.setPages(list, new MZHolderCreator<BannerViewHolder>() {
//                            @Override
//                            public BannerViewHolder createViewHolder() {
//                                return new BannerViewHolder();
//                            }
//                        });
//                        bannerView.start();
//                    }
//                });
        netWork!!.getApi(Api::class.java).getNewUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<UpdateModule>(this) {
                    override fun onFail(e: ApiError) {}
                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {}
                    override fun onSuccess(updateModule: UpdateModule?) {
                        if (updateModule!!.fromwhich == "1") {
                            val intent = Intent(this@MainActivity, WebActivity::class.java)
                            intent.putExtra("url", updateModule!!.weburl)
                            startActivity(intent)
                        } else if (updateModule!!.fromwhich == "2") {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateModule!!.weburl))
                            startActivity(intent)
                            finish()
                        }
                    }
                })
    }

    override fun initLisenter() {
        bottom_navigation_bar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                if (data!!.flag!!) {
                    when (position) {
                        0 -> {
                            switchFragment(homeFragment)
                            SPUtils.getInstance().put("index", position)
                        }
                        1 -> {
                            switchFragment(vBangFragment)
                            SPUtils.getInstance().put("index", position)
                        }
                        2 -> {
                            if (SPUtils.getInstance().getString("logininfo").isBlank()) {
                                var index = SPUtils.getInstance().getInt("index", 0)
                                when (index) {
                                    0 -> {
                                        bottom_navigation_bar.selectTab(0)
                                        SPUtils.getInstance().put("index", position)
                                    }
                                    1 -> {
                                        bottom_navigation_bar.selectTab(1)
                                        SPUtils.getInstance().put("index", position)
                                    }
                                    3 -> {
                                        bottom_navigation_bar.selectTab(3)
                                        SPUtils.getInstance().put("index", position)
                                    }
                                    4 -> {
                                        bottom_navigation_bar.selectTab(4)
                                        SPUtils.getInstance().put("index", position)
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
                            SPUtils.getInstance().put("index", position)
                        }
                        4 -> {
                            switchFragment(yueDanFragment)
                            SPUtils.getInstance().put("index", position)
                        }
                        else -> switchFragment(homeFragment)
                    }
                } else {
                    when (position) {
                        0 -> {
                            switchFragment(homeFragment)
                            SPUtils.getInstance().put("index", position)
                        }
                        1 -> {
                            switchFragment(vBangFragment)
                            SPUtils.getInstance().put("index", position)
                        }
                        2 -> {
                            switchFragment(findFragment)
                            SPUtils.getInstance().put("index", position)
                        }
                        3 -> {
                            switchFragment(keFuFragment)
                            SPUtils.getInstance().put("index", position)
                        }
                        4 -> {
                            if (SPUtils.getInstance().getString("logininfo").isBlank()) {
                                var index = SPUtils.getInstance().getInt("index", 0)
                                when (index) {
                                    0 -> {
                                        SPUtils.getInstance().put("index", position)
                                        bottom_navigation_bar.selectTab(0)
                                    }
                                    1 -> {
                                        SPUtils.getInstance().put("index", position)
                                        bottom_navigation_bar.selectTab(1)
                                    }
                                    2 -> {
                                        SPUtils.getInstance().put("index", position)
                                        bottom_navigation_bar.selectTab(2)
                                    }
                                    3 -> {
                                        SPUtils.getInstance().put("index", position)
                                        bottom_navigation_bar.selectTab(3)
                                    }
                                }
                                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                return
                            } else {
                                switchFragment(yueDanFragment)
                            }
                        }
                    }
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
                bottom_navigation_bar.selectTab(3)
                SPUtils.getInstance().put("index", 3)
            }
            "news" -> {
                bottom_navigation_bar.selectTab(2)
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
            if (secondTime - lasttime < 2000) {
                exitProcess(0)
            } else {
                ToastUtils.showShort("再按一次返回键退出")
                lasttime = System.currentTimeMillis()
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
