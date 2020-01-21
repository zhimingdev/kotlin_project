package com.test.sandev.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.test.sandev.R
import com.test.sandev.adapter.WatchHistoryAdapter
import com.test.sandev.base.BaseActivity
import com.test.sandev.constans.UrlConstans
import com.test.sandev.module.HomeNewBean
import com.test.sandev.utils.StatusBarUtil
import com.test.sandev.utils.WatchHistoryUtils
import kotlinx.android.synthetic.main.layout_watch_history.*
import java.util.*
import kotlin.collections.ArrayList

class WatchHistoryActivity : BaseActivity() {

    var itemListData :ArrayList<HomeNewBean.Issue.Item>? = null

    companion object {
        private const val HISTORY_MAX = 20
    }

    override fun getLayoutId(): Int = R.layout.layout_watch_history

    override fun initData() {
        multipleStatusView.showLoading()
        itemListData = queryWatchHistory()
        //返回
        toolbar.setNavigationOnClickListener { finish() }

        val mAdapter = WatchHistoryAdapter(this, itemListData!!, R.layout.item_video_small_card)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        if (itemListData!!.size > 0) {
            multipleStatusView.showContent()
        } else {
            multipleStatusView.showEmpty()
        }

        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
    }

    /**
     * 查询观看的历史记录
     */
    private fun queryWatchHistory(): ArrayList<HomeNewBean.Issue.Item> {
        val watchList = ArrayList<HomeNewBean.Issue.Item>()
        watchList.clear()
        val hisAll = WatchHistoryUtils.getAll(UrlConstans.FILE_WATCH_HISTORY_NAME, this) as Map<*, *>
        //将key排序升序
        val keys = hisAll.keys.toTypedArray()
        Arrays.sort(keys)
        val keyLength = keys.size
        //这里计算 如果历史记录条数是大于 可以显示的最大条数，则用最大条数做循环条件，防止历史记录条数-最大条数为负值，数组越界
        val hisLength = if (keyLength > HISTORY_MAX) HISTORY_MAX else keyLength
        // 反序列化和遍历 添加观看的历史记录
        (1..hisLength).mapTo(watchList) {
            WatchHistoryUtils.getObject(UrlConstans.FILE_WATCH_HISTORY_NAME, this,
                    keys[keyLength - it] as String) as HomeNewBean.Issue.Item
        }

        return watchList
    }

}