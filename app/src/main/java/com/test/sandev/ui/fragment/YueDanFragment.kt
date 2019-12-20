package com.test.sandev.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.*
import com.bumptech.glide.Glide
import com.robinhood.ticker.TickerUtils
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.constans.UrlConstans
import com.test.sandev.module.*
import com.test.sandev.ui.activity.LoginActivity
import com.test.sandev.ui.activity.SecurpacActivity
import com.test.sandev.ui.activity.WebActivity
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import com.test.sandev.utils.RxUtil
import com.test.sandev.utils.UsualDialogger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class YueDanFragment : BaseFragment() {

    private var cacheDialog: com.test.sandev.utils.UsualDialogger? = null
    private var logoutdialog: com.test.sandev.utils.UsualDialogger? = null
    var weburl : String = UrlConstans.kefu_url
    val network by lazy { NetWork() }

    override fun initView(): View {
        EventBus.getDefault().register(this)
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_mine, null)
        return view
    }


    override fun initSandevDate() {
        showName()
        getPackInfo()
        tv_version.text = "V 1.0.0"
        tv_cache.setCharacterList(TickerUtils.getDefaultListForUSCurrency())
        tv_cache.text = "0 B"
        tv_cache.text = FileUtils.getSize(Utils.getApp().cacheDir)
        inv.setNumber("1")
    }

    private fun getPackInfo() {
        network.getApi(Api::class.java).getPack()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<PackModule>(activity!!){
                    override fun onSuccess(t: PackModule?) {
                        if (t!!.flag!!) {
                            rl_my_jifen.visibility = View.VISIBLE
                        }
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    private fun getMineData() {
        network.getApi(Api::class.java).getMineUrl()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<MineModule>(activity!!){
                    override fun onSuccess(t: MineModule?) {
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    private fun showName() {
        var name = SPUtils.getInstance().getString("logininfo", "")
        if (name.isNotBlank()) {
            tv_uaername.text = name
            ll_login.isEnabled = false
            tv_logout.visibility = View.VISIBLE
            iv_head.setImageResource(R.mipmap.ic_head)
            tv_collect.text = (0..100).random().toString()
            tv_starts.text = (0..50).random().toString()
        } else {
            tv_uaername.text = "立即登录"
            ll_login.isEnabled = true
            tv_logout.visibility = View.GONE
            tv_collect.text = "0"
            tv_starts.text = "0"
            iv_head.setImageResource(R.mipmap.img_mine)
        }
    }


    private fun getJifen() {
        var id = SPUtils.getInstance().getInt("loginid")
        var map : MutableMap<String,Int> = mutableMapOf()
        map["userId"] = id
        network.getWanApi(Api::class.java).getUserJiFen(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :ApiBaseResponse<JifenModule>(activity!!) {
                    override fun onSuccess(t: JifenModule?) {
                        tv_count.text = t!!.coinCount.toString()
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }


    override fun initSandevListenter() {
        rl_cache.setOnClickListener {
            cacheDialog =
                    com.test.sandev.utils.UsualDialogger.Builder(activity)
                            .setTitle("温馨提示")
                            .setMessage("您确定要清除缓存吗？")
                            .setOnConfirmClickListener("确定", object : com.test.sandev.utils.UsualDialogger.onConfirmClickListener {
                                override fun onClick(view: View) {
                                    clearCache()
                                    cacheDialog!!.dismiss()
                                }
                            })
                            .setOnCancelClickListener("取消", object : com.test.sandev.utils.UsualDialogger.onCancelClickListener {
                                override fun onClick(view: View) {
                                    cacheDialog!!.dismiss()
                                }

                            }).build().shown()
        }
        ll_login.setOnClickListener {
            var intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        tv_logout.setOnClickListener {
            logoutdialog =
                    com.test.sandev.utils.UsualDialogger.Builder(activity)
                            .setTitle("温馨提示")
                            .setMessage("确定要退出登录吗？")
                            .setOnConfirmClickListener("确定", object : com.test.sandev.utils.UsualDialogger.onConfirmClickListener {
                                override fun onClick(view: View) {
                                    SPUtils.getInstance().clear()
                                    showName()
                                    logoutdialog!!.dismiss()
                                }
                            })
                            .setOnCancelClickListener("取消", object : com.test.sandev.utils.UsualDialogger.onCancelClickListener {
                                override fun onClick(view: View) {
                                    logoutdialog!!.dismiss()
                                }

                            }).build().shown()

        }

        rl_web.setOnClickListener {
            var intent = Intent(context,WebActivity::class.java)
            intent.putExtra("url",weburl)
            startActivity(intent)
        }

        rl_my_jifen.setOnClickListener {
            var intent = Intent(activity, SecurpacActivity::class.java)
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
    public fun onEvent(msg: MessageEvent) {
        when (msg.message) {
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