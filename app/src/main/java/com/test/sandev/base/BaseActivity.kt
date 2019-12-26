package com.test.sandev.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.sandev.utils.ActivityUtil

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityUtil.addActivity(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(getLayoutId())
        initData()
        initLisenter()
    }

    /**
     * 初始化数据
     */
    open protected fun initData() {

    }

    /**
     * 初始化监听事件
     */
    open protected fun initLisenter() {

    }

    /**
     *  获取布局Id
     */
    abstract fun getLayoutId(): Int
}