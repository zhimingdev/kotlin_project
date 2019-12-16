package com.test.myapplication.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.*
import com.bumptech.glide.Glide
import com.robinhood.ticker.TickerUtils
import com.test.myapplication.R
import com.test.myapplication.base.BaseFragment
import com.test.myapplication.module.MessageEvent
import com.test.myapplication.ui.activity.LoginActivity
import com.test.myapplication.utils.RxUtil
import com.test.myapplication.utils.UsualDialogger
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class YueDanFragment : BaseFragment() {

    private var cacheDialog: UsualDialogger? = null

    override fun initView(): View {
        EventBus.getDefault().register(this)
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_mine, null)
        return view
    }


    override fun initDate() {
        showName()
        tv_version.text = "V " + AppUtils.getAppVersionCode()
        tv_cache.setCharacterList(TickerUtils.getDefaultListForUSCurrency())
        tv_cache.text = "0 B"
        tv_cache.text = FileUtils.getSize(Utils.getApp().cacheDir)
    }

    private fun showName() {
        var name = SPUtils.getInstance().getString("logininfo", "")
        if (name.isNotBlank()) {
            tv_uaername.text = name
            ll_login.isEnabled = false
        }else{
            tv_uaername.text = "立即登录"
            ll_login.isEnabled = true
        }
    }


    override fun initListenter() {
        rl_cache.setOnClickListener {
            cacheDialog =
                UsualDialogger.Builder(context)
                    .setTitle("温馨提示")
                    .setMessage("您确定要清除缓存吗？")
                    .setOnConfirmClickListener("确定", object : UsualDialogger.onConfirmClickListener {
                        override fun onClick(view: View) {
                            clearCache()
                            cacheDialog!!.dismiss()
                        }
                    })
                    .setOnCancelClickListener("取消", object : UsualDialogger.onCancelClickListener {
                        override fun onClick(view: View) {
                            cacheDialog!!.dismiss()
                        }

                    }).build().shown()
        }
        ll_login.setOnClickListener {
            var intent = Intent(activity,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * 清除缓存
     */
    fun clearCache() {
        RxUtil.switchThread(object : RxUtil.OnEventListener {
            override fun onEvent(event: Any?) {
                Glide.get(context).clearDiskCache()
                CleanUtils.cleanInternalCache()
                CleanUtils.cleanExternalCache()
            }
        }, object : RxUtil.OnEventListener {
            override fun onEvent(event: Any?) {
                tv_cache.text = "0 B"
                ToastUtils.showShort("缓存清除成功")
            }
        })
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEvent(msg : MessageEvent) {
        when(msg.message) {
            "success" -> {
                showName()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        showName()
    }

}