package com.test.sandev.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.test.sandev.R
import com.test.sandev.base.BaseFragment
import com.test.sandev.ui.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_alive.*

class AliveFragment : BaseFragment() {

    companion object{
        fun getInstance() : AliveFragment{
            val fragment = AliveFragment()
            return fragment
        }
    }

    override fun initView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_alive,null)
        return view
    }

    override fun initSandevListenter() {
        tv_login.setOnClickListener {
            val intent = Intent(activity!!,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}