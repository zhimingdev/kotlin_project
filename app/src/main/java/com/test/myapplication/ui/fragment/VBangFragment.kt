package com.test.myapplication.ui.fragment

import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.widget.TextView
import com.test.myapplication.R
import com.test.myapplication.base.BaseFragment

class VBangFragment : BaseFragment() {

    override fun initView(): View {
        val textview = TextView(activity)
        textview.gravity= Gravity.CENTER
        textview.setTextColor(resources.getColor(android.R.color.holo_green_light))
        textview.text = "测试"
        return textview
    }
}