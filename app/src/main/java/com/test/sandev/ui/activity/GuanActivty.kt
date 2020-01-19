package com.test.sandev.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.Calendar.Scheme
import com.haibin.calendarview.CalendarView
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import kotlinx.android.synthetic.main.activity_item.*

class GuanActivty : BaseActivity(), CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_item
    }

    override fun initData() {
        var add = intent.getStringExtra("address")
        var name = intent.getStringExtra("stadiumItemName")
        var phone = intent.getStringExtra("telephone")
        var url = intent.getStringExtra("imageUrl")
        Picasso.with(this).load(url).into(iv_dea_guan)
        tv_deta_name.text = name
        tv_deta_phone.text = phone
        tv_deta_address.text = add

        val year: Int = calendarView.curYear
        val month: Int = calendarView.curMonth
        val map: MutableMap<String, Calendar> = mutableMapOf()
        map.clear()
        map[getSchemeCalendar(year, month, 3, -0xbf24db, "假").toString()] = getSchemeCalendar(year, month, 3, -0xbf24db, "假")
        map[getSchemeCalendar(year, month, 6, -0x196ec8, "事").toString()] = getSchemeCalendar(year, month, 6, -0x196ec8, "事")
        map[getSchemeCalendar(year, month, 9, -0x20ecaa, "议").toString()] = getSchemeCalendar(year, month, 9, -0x20ecaa, "议")
        map[getSchemeCalendar(year, month, 13, -0x123a93, "记").toString()] = getSchemeCalendar(year, month, 13, -0x123a93, "记")
        map[getSchemeCalendar(year, month, 14, -0x123a93, "记").toString()] = getSchemeCalendar(year, month, 14, -0x123a93, "记")
        map[getSchemeCalendar(year, month, 15, -0x5533bc, "假").toString()] = getSchemeCalendar(year, month, 15, -0x5533bc, "假")
        map[getSchemeCalendar(year, month, 18, -0x43ec10, "记").toString()] = getSchemeCalendar(year, month, 18, -0x43ec10, "记")
        map[getSchemeCalendar(year, month, 25, -0xec5310, "假").toString()] = getSchemeCalendar(year, month, 25, -0xec5310, "假")
        map[getSchemeCalendar(year, month, 27, -0xec5310, "多").toString()] = getSchemeCalendar(year, month, 27, -0xec5310, "多")
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        calendarView.setSchemeDate(map)
    }

    private fun getSchemeCalendar(year: Int, month: Int, day: Int, color: Int, text: String): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color //如果单独标记颜色、则会使用这个颜色
        calendar.scheme = text
        calendar.addScheme(Scheme())
        calendar.addScheme(-0xff7800, "假")
        calendar.addScheme(-0xff7800, "节")
        return calendar
    }

    override fun initLisenter() {
        calendarView.setOnCalendarSelectListener(this)
        calendarView.setOnYearChangeListener(this)
        tv_yy.setOnClickListener {
            var loginid = SPUtils.getInstance().getInt("loginid")
            if (loginid != -1) {
                ToastUtils.showShort("预订成功")
                finish()
            } else {
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        rl_guan_back.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
        var day = calendar!!.day
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
    }

    override fun onYearChange(year: Int) {

    }
}