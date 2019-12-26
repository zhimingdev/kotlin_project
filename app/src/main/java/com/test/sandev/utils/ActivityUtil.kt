package com.test.sandev.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.text.TextUtils
import java.util.Stack
import kotlin.system.exitProcess

class ActivityUtil
/**
 * 私有构造器 无法外部创建
 */
private constructor() {
    companion object {
        /**
         * Activity堆栈 Stack:线程安全
         */
        var mActivityStack: Stack<Activity>? = Stack()
        private val TAG = "ActivityUtil"
        /**
         * 单一实例
         */
        private var sActivityUtil: ActivityUtil? = null

        /**
         * 获取单一实例 双重锁定
         * @return this
         */
        val instance: ActivityUtil
            get() {
                if (sActivityUtil == null) {
                    synchronized(ActivityUtil::class.java) {
                        if (sActivityUtil == null) {
                            sActivityUtil = ActivityUtil()
                        }
                    }
                }
                return sActivityUtil!!
            }

        /**
         * 判断某个Activity 界面是否在前台
         * @param context
         * @param className 某个界面名称
         * @return
         */
        fun isForeground(context: Context?, className: String): Boolean {
            if (context == null || TextUtils.isEmpty(className)) {
                return false
            }

            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val list = am.getRunningTasks(1)
            if (list != null && list.size > 0) {
                val cpn = list[0].topActivity
                if (className == cpn!!.className) {
                    return true
                }
            }
            return false
        }

        /**
         * 获取当前Activity (堆栈中最后一个添加的)
         * @return Activity
         */
        val currentActivity: Activity
            get() = mActivityStack!!.lastElement()

        /**
         * 添加Activity到堆栈
         */
        fun addActivity(activity: Activity) {
            mActivityStack!!.add(activity)
        }

        /**
         * 移除堆栈中的Activity
         * @param activity Activity
         */
        fun removeActivity(activity: Activity?) {
            if (activity != null && mActivityStack!!.contains(activity)) {
                mActivityStack!!.remove(activity)
            }
        }

        /**
         * 获取指定类名的Activity
         */
        fun getActivity(cls: Class<*>): Activity? {
            if (mActivityStack != null)
                for (activity in mActivityStack!!) {
                    if (activity.javaClass == cls) {
                        return activity
                    }
                }
            return null
        }

        /**
         * 结束当前Activity (堆栈中最后一个添加的)
         */
        fun finishCurrentActivity() {
            val activity = mActivityStack!!.lastElement()
            finishActivity(activity)
        }

        /**
         * 结束指定的Activity
         * @param activity Activity
         */
        fun finishActivity(activity: Activity?) {
            if (activity != null && mActivityStack!!.contains(activity)) {
                mActivityStack!!.remove(activity)
                activity.finish()
            }
        }

        /**
         * 结束指定类名的Activity
         * @param clazz Activity.class
         */
        fun finishActivity(clazz: Class<*>) {
            for (activity in mActivityStack!!) {
                if (activity.javaClass == clazz) {
                    finishActivity(activity)
                    break
                }
            }
        }

        /**
         * 结束所有Activity
         */
        fun finishAllActivity() {

            for (i in mActivityStack!!.indices.reversed()) {

                if (mActivityStack!![i] != null) {
                    finishActivity(mActivityStack!![i])
                }
            }
            mActivityStack!!.clear()
        }

        /**
         * 结束某个Activity之外的所有Activity
         */
        fun finishAllActivityExcept(clazz: Class<*>) {
            for (i in mActivityStack!!.indices.reversed()) {

                if (mActivityStack!![i] != null && mActivityStack!![i].javaClass != clazz) {
                    finishActivity(mActivityStack!![i])
                }
            }
        }

        /**
         * 退出应用程序
         */
        fun exitApp() {
            try {
                finishAllActivity()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                exitProcess(0)
            }
        }
    }
}
