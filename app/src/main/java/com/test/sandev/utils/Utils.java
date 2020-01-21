package com.test.sandev.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import com.test.sandev.MyApplication;

/**
 * 普通工具类
 *
 * 作用：封装了常用零碎的功能
 */
public class Utils {

    //获取Context对象
    public static Context getContext() {
        return MyApplication.Companion.getInstances();
    }

    /*====================================加载资源文件===============================================*/

    //获取strings.xml文件中的字符串
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    //获取strings.xml文件中的字符串数组
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    //获取colors.xml文件中的颜色
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    //获取dimens.xml文件中的尺寸(单位为像素)
    public static int getPixelDimem(int id) {
        return getContext().getResources().getDimensionPixelOffset(id);
    }

    /*===========================================dp与px转化========================================*/

    //dp转化为px
    public static int dp2px(float dp) {
        //获取设备密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    //px转化为dp
    public static float px2dp(int px) {
        //获取设备密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    /*====================================加载布局文件==============================================*/

    //将布局文件转化为View对象
    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

}
