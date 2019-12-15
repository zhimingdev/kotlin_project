package com.test.myapplication.ui.fragment

import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.*
import com.bumptech.glide.Glide
import com.robinhood.ticker.TickerUtils
import com.test.myapplication.R
import com.test.myapplication.base.BaseFragment
import com.test.myapplication.utils.RxUtil
import com.test.myapplication.utils.UsualDialogger
import kotlinx.android.synthetic.main.fragment_mine.*

class YueDanFragment : BaseFragment() {

    private var cacheDialog: UsualDialogger? = null

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_mine, null)
        return view
    }

    override fun initDate() {
        tv_version.text = "V " + AppUtils.getAppVersionCode()
        tv_cache.setCharacterList(TickerUtils.getDefaultListForUSCurrency())
        tv_cache.text = "0 B"
        tv_cache.text = FileUtils.getSize(Utils.getApp().cacheDir)
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
    }

    /**
     * 清除缓存
     */
    fun clearCache() {
        RxUtil.switchThread(object : RxUtil.OnEventListener {
            override fun onEvent(event: Any?): Any {
                Glide.get(context).clearDiskCache()
                CleanUtils.cleanInternalCache()
                CleanUtils.cleanExternalCache()
                return event!!
            }
        }, object : RxUtil.OnEventListener {
            override fun onEvent(event: Any?): Any {
                tv_cache.text = "0 B"
                ToastUtils.showShort("缓存清除成功")
                return event!!
            }
        })
    }
}