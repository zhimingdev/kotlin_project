package com.test.myapplication.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.test.myapplication.R
import com.test.myapplication.api.Api
import com.test.myapplication.base.BaseActivity
import com.test.myapplication.module.ApiError
import com.test.myapplication.module.BaseResponse
import com.test.myapplication.module.MessageEvent
import com.test.myapplication.module.UserModule
import com.test.myapplication.utils.ApiBaseResponse
import com.test.myapplication.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseActivity(), TextWatcher {

    val network by lazy { NetWork() }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
        fab.setOnClickListener {
            window.exitTransition = null
            window.enterTransition = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.transitionName)
                startActivity(Intent(this, RegisterActivity::class.java), options.toBundle())
            } else {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    override fun initLisenter() {
        et_username.addTextChangedListener(this)
        et_password.addTextChangedListener(this)
        bt_go.setOnClickListener {
            if (et_username.text.toString().trim().isBlank()) {
                ToastUtils.showShort("用户名不能为空")
                return@setOnClickListener
            }
            if (et_password.text.toString().trim().isBlank()){
                ToastUtils.showShort("密码不能为空")
                return@setOnClickListener
            }
            goLogin(et_username.text.toString().trim(),et_password.text.toString().trim())
        }
    }

    private fun goLogin(name: String, pwd: String) {
        var map: MutableMap<String,String> = mutableMapOf()
        map["username"] = name
        map["password"] = pwd
        network.getWanApi(Api::class.java).getUserLogin(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<UserModule>(this) {
                    override fun onSuccess(t: UserModule?) {
                        SPUtils.getInstance().put("logininfo",t!!.nickname)
                        SPUtils.getInstance().put("loginid",t!!.id)
                        EventBus.getDefault().post(MessageEvent("success"))
                        finish()
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                        ToastUtils.showShort(tBaseReponse.errorMsg)
                    }

                    override fun onFail(e: ApiError) {

                    }

                })
    }

    override fun afterTextChanged(s: Editable?) {
        if (et_password.text.toString().trim().isNotBlank() && et_password.text.toString().trim().isNotBlank()) {
            bt_go.isEnabled = true
            bt_go.background = resources.getDrawable(R.drawable.bt_select_shape)
            bt_go.setTextColor(resources.getColor(R.color.white))
        }else{
            bt_go.isEnabled = false
            bt_go.setTextColor(Color.parseColor("#d3d3d3"))
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}