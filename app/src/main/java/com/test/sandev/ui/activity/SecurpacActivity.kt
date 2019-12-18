package com.test.sandev.ui.activity

import android.content.Intent
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import kotlinx.android.synthetic.main.activity_juan.*

class SecurpacActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_juan
    }

    override fun initLisenter() {
        rl_sec_back.setOnClickListener {
            finish()
        }
        iv_pack.setOnClickListener {
            var intent = Intent(this,KeFuActivty::class.java)
            startActivity(intent)
        }
    }
}