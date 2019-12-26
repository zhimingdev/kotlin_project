package com.test.sandev.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

object DataString {
    private var mYear: String? = null
    private var mMonth: String? = null
    private var mDay: String? = null
    private var mWay: String? = null

    fun StringData(): String {
        val c = Calendar.getInstance()
        c.timeZone = TimeZone.getTimeZone("GMT+8:00")
        mYear = c.get(Calendar.YEAR).toString() // 获取当前年份
        mMonth = (c.get(Calendar.MONTH) + 1).toString()// 获取当前月份
        mDay = c.get(Calendar.DAY_OF_MONTH).toString()// 获取当前月份的日期号码
        mWay = c.get(Calendar.DAY_OF_WEEK).toString()
        if ("1" == mWay) {
            mWay = "天"
        } else if ("2" == mWay) {
            mWay = "一"
        } else if ("3" == mWay) {
            mWay = "二"
        } else if ("4" == mWay) {
            mWay = "三"
        } else if ("5" == mWay) {
            mWay = "四"
        } else if ("6" == mWay) {
            mWay = "五"
        } else if ("7" == mWay) {
            mWay = "六"
        }
        return mYear + "年" + mMonth + "月" + mDay + "日" + "  周" + mWay
    }

    fun getWeek(pTime: String): String {
        var Week = ""
        val format = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        try {
            c.time = format.parse(pTime)!!
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val wek = c.get(Calendar.DAY_OF_WEEK)

        if (wek == 1) {
            Week += "周日"
        }
        if (wek == 2) {
            Week += "周一"
        }
        if (wek == 3) {
            Week += "周二"
        }
        if (wek == 4) {
            Week += "周三"
        }
        if (wek == 5) {
            Week += "周四"
        }
        if (wek == 6) {
            Week += "周五"
        }
        if (wek == 7) {
            Week += "周六"
        }
        return Week
    }

}
