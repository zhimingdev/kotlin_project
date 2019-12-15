package com.test.myapplication.ui.fragment

import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.test.myapplication.base.BaseFragment

class MVFragment : BaseFragment() {

    override fun initView(): View {
        val textview = TextView(activity)
        textview.gravity= Gravity.CENTER
        textview.text = "测试"
        return textview
    }
}