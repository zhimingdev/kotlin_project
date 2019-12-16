package com.test.myapplication.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import com.blankj.utilcode.util.ToastUtils
import com.test.myapplication.R
import com.test.myapplication.api.Api
import com.test.myapplication.base.BaseActivity
import com.test.myapplication.module.ApiError
import com.test.myapplication.module.BaseResponse
import com.test.myapplication.module.UserModule
import com.test.myapplication.utils.ApiBaseResponse
import com.test.myapplication.utils.NetWork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.et_password
import kotlinx.android.synthetic.main.activity_register.et_username
import kotlinx.android.synthetic.main.activity_register.fab


class RegisterActivity : BaseActivity(), TextWatcher {

    val network by lazy { NetWork() }

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation()
        }
    }

    override fun initLisenter() {
        fab.setOnClickListener {
            animateRevealClose()
        }
        et_username.addTextChangedListener(this)
        et_password.addTextChangedListener(this)
        et_repeatpassword.addTextChangedListener(this)

        findViewById<Button>(R.id.bt_register).setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                var username = et_username.text.toString().trim()
                var pwd = et_password.text.toString().trim()
                var rewpwd = et_repeatpassword.text.toString().trim()
                if (username.isBlank()) {
                    ToastUtils.showShort("用户名不能为空")
                    return
                }
                if (pwd.isBlank() || rewpwd.isBlank()) {
                    ToastUtils.showShort("密码不能为空")
                    return
                }
                if (pwd == rewpwd) {
                    registerUser(username,pwd,rewpwd)
                }else{
                    ToastUtils.showShort("两次密码不一致,请重新输入")
                    et_password.text = Editable.Factory.getInstance().newEditable("")
                    et_repeatpassword.text = Editable.Factory.getInstance().newEditable("")
                    return
                }
            }

        })
    }

    private fun registerUser(username: String, pwd: String, rewpwd: String) {
        var map : MutableMap<String,String> = mutableMapOf()
        map["username"] = username
        map["password"] = pwd
        map["repassword"] = rewpwd
        network.getWanApi(Api::class.java).getUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :ApiBaseResponse<UserModule>(this){
                    override fun onSuccess(t: UserModule?) {
                        ToastUtils.showShort("注册成功")
                        finish()
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                        ToastUtils.showShort(tBaseReponse.errorMsg)
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    private fun ShowEnterAnimation() {
        val transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition)
        window.sharedElementEnterTransition = transition

        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {
                cv_add.visibility = View.GONE
            }

            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                animateRevealShow()
            }

            override fun onTransitionCancel(transition: Transition) {

            }

            override fun onTransitionPause(transition: Transition) {

            }

            override fun onTransitionResume(transition: Transition) {

            }


        })
    }

    fun animateRevealShow() {
        val mAnimator =
            ViewAnimationUtils.createCircularReveal(cv_add, cv_add.width / 2, 0, (fab.width / 2).toFloat(), (cv_add.height).toFloat())
        mAnimator.duration = 500
        mAnimator.interpolator = AccelerateInterpolator()
        mAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
            }

            override fun onAnimationStart(animation: Animator) {
                cv_add.visibility = View.VISIBLE
                super.onAnimationStart(animation)
            }
        })
        mAnimator.start()
    }

    private fun animateRevealClose() {
        val mAnimator =
            ViewAnimationUtils.createCircularReveal(cv_add, cv_add.width / 2, 0, cv_add.height.toFloat(), (fab.width / 2).toFloat())
        mAnimator.duration = 500
        mAnimator.interpolator = AccelerateInterpolator()
        mAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                cv_add.visibility = View.INVISIBLE
                super.onAnimationEnd(animation)
                fab.setImageResource(R.drawable.plus)
                super@RegisterActivity.onBackPressed()
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
            }
        })
        mAnimator.start()
    }

    override fun onBackPressed() {
        animateRevealClose()
    }

    override fun afterTextChanged(s: Editable?) {
        bt_register.isEnabled = et_password.text.toString().trim().isNotEmpty() &&
                et_password.text.toString().trim().isNotBlank() &&
                et_repeatpassword.text.toString().trim().isNotBlank()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}
