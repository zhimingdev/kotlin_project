package com.test.sandev.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.test.sandev.R
import com.test.sandev.adapter.SubjectAdapter
import com.test.sandev.base.BaseFragment
import com.test.sandev.constans.UrlConstans
import com.test.sandev.module.HomeNewBean
import com.test.sandev.module.Subject
import com.test.sandev.utils.NetWork
import com.test.sandev.utils.Utils
import kotlinx.android.synthetic.main.fragment_list_common.*

class CommonZhunahuiListViewFragment(private var number: Int) : BaseFragment(), Response.Listener<String>, Response.ErrorListener {

    val netWork by lazy { NetWork() }

    private var itemList = ArrayList<HomeNewBean.Issue.Item>()

    companion object {
        fun getInstanca(type: Int): CommonZhunahuiListViewFragment {
            return CommonZhunahuiListViewFragment(type)
        }
    }

    init {
        ClassicsFooter.REFRESH_FOOTER_PULLING = "上拉加载更多"
        ClassicsFooter.REFRESH_FOOTER_RELEASE = "释放立即加载"
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = "正在刷新..."
        ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载..."
        ClassicsFooter.REFRESH_FOOTER_FINISH = "加载完成"
        ClassicsFooter.REFRESH_FOOTER_FAILED = "加载失败"
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "没有更多数据了"
    }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_list_common, null)
        return view
    }

    override fun initSandevDate() {
        loadData()
    }

    override fun initSandevListenter() {
        srl_list.setOnRefreshListener {
            loadData()
            it.finishRefresh()
        }

        srl_list.setOnLoadMoreListener {
            it.finishLoadMore()
        }
    }
    private fun loadData() {
        if (number == 8) {
            //创建请求队列
            val requestQueue = Volley.newRequestQueue(context)
            //创建请求
            val stringRequest = StringRequest(UrlConstans.URL_SUBJECT, this, this)
            //将请求添加到请求队列
            requestQueue.add(stringRequest)
        }
    }

    override fun onResponse(s: String?) {
        //Json解析
        //Json解析
        val gson = Gson()
        var subject = gson.fromJson<Subject>(s, Subject::class.java)
        //数据集
        var articles = subject.articles
        //listview适配器
        var subjectAdapter = SubjectAdapter(articles)
        listview.adapter = subjectAdapter
        subjectAdapter.notifyDataSetChanged()
        listview.setOnItemClickListener { parent, view, position, id ->

        }
    }

    override fun onErrorResponse(p0: VolleyError?) {
    }
}