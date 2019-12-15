package com.test.myapplication.ui.activity

import android.content.Intent
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.test.myapplication.R
import com.test.myapplication.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity(), ViewPropertyAnimatorListener {
    override fun onAnimationEnd(view: View?) {
        var intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onAnimationStart(view: View?) {
    }

    override fun onAnimationCancel(view: View?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        ViewCompat.animate(imageView).setDuration(2000).scaleX(1.0f).scaleY(1.0f).setListener(this).start()
    }
}