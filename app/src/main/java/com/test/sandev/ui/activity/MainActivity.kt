package com.test.sandev.ui.activity

import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess
import android.widget.Toast
import android.view.KeyEvent
import androidx.core.view.get
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseActivity
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.PackModule
import com.test.sandev.ui.fragment.KeFuFragment
import com.test.sandev.ui.fragment.VBangFragment
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.FragmentUtils
import com.test.sandev.utils.NetWork
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : BaseActivity() {
    //需要显示的fragment
    var showfragment: Fragment? = null
    //当前显示的fragment
    var currentfragment: Fragment? = null
    var lasttime: Long = 0
    val network by lazy { NetWork() }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        network.getApi(Api::class.java).getPack()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<PackModule>(this) {
                    override fun onSuccess(t: PackModule?) {
                        println("======" + t!!.flag)
                        if (t!!.flag!!) {
                            bottomBar.getTabWithId(R.id.tab_yuedan).badgeHidesWhenActive = false
                            bottomBar.getTabWithId(R.id.tab_yuedan).setBadgeCount(1)
                        }
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    override fun initLisenter() {
        bottomBar.setOnTabSelectListener {
            var transaction = supportFragmentManager.beginTransaction()
            showfragment = FragmentUtils.instance.getFragment(it)
            if (currentfragment != null && showfragment != currentfragment) {
                transaction.hide(currentfragment!!)
            }
            if (showfragment!!.isAdded) {
                transaction.show(FragmentUtils.instance.getFragment(it))
            } else {
                transaction.add(R.id.container, showfragment!!)
            }
            transaction.commit()
            currentfragment = showfragment
        }
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
}
