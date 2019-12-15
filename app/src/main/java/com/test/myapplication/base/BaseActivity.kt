package com.test.myapplication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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