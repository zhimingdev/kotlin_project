package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import com.test.sandev.base.BaseFragment
import kotlinx.android.synthetic.main.no_fragemnt.*

class NoFragment : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.no_fragemnt
    }

    override fun initLisenter() {
        rl_data_back.setOnClickListener {
            finish()
        }

    }
}