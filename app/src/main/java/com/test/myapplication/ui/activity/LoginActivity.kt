package com.test.myapplication.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import com.test.myapplication.R
import com.test.myapplication.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
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
}