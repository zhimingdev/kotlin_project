package com.test.sandev.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.test.sandev.R
import com.test.sandev.adapter.CategoryDetailAdapter
import com.test.sandev.adapter.LeagueAdapter
import com.test.sandev.base.BaseFragment
import com.test.sandev.constans.UrlConstans
import com.test.sandev.module.HomeNewBean
import com.test.sandev.module.League
import com.test.sandev.ui.activity.WebActivity
import com.test.sandev.utils.NetWork
import kotlinx.android.synthetic.main.fragment_list_common.*

class CommonListViewFragment(private var number: Int) : BaseFragment(), Response.Listener<String>, Response.ErrorListener {

    val netWork by lazy { NetWork() }

    private var nextPageUrl: String? = null
    private var itemList = ArrayList<HomeNewBean.Issue.Item>()
    private val mResultAdapter by lazy { CategoryDetailAdapter(activity!!, itemList, R.layout.item_category_detail) }

    companion object {
        fun getInstanca(type: Int): CommonListViewFragment {
            return CommonListViewFragment(type)
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
      if (number == 7) {
            //创建请求队列
            val requestQueue = Volley.newRequestQueue(context)
            //创建请求
            val stringRequest = StringRequest(UrlConstans.URL_Transfer, this, this)
            //将请求添加到请求队列
            requestQueue.add(stringRequest)
        }else if (number == 8) {
          //创建请求队列
          val requestQueue = Volley.newRequestQueue(context)
          //创建请求
          val stringRequest = StringRequest(UrlConstans.URL_CHINA, this, this)
          //将请求添加到请求队列
          requestQueue.add(stringRequest)
      }
    }

    override fun onResponse(s: String?) {
        //Json解析
        //Json解析
        val gson = Gson()
        var league = gson.fromJson<League>(s, League::class.java)
        var articles = handleList(league.articles)
        //listview适配器
        var leagueAdapter = LeagueAdapter(articles)
        listview.adapter = leagueAdapter
        leagueAdapter.notifyDataSetChanged()
        listview.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(activity,WebActivity::class.java)
            intent.putExtra("url", articles!![position].url)
            startActivity(intent)
        }
    }

    override fun onErrorResponse(p0: VolleyError?) {
    }

    //去除圈子、直播和广告
    private fun handleList(articles: MutableList<League.Articles>): List<League.Articles>? {
        val iterator: MutableIterator<League.Articles> = articles.iterator()
        while (iterator.hasNext()) {
            val article: League.Articles = iterator.next()
            if (!article.url.contains("http")) {
                iterator.remove()
            } else if (article.label != null && article.label == "推广") {
                iterator.remove()
            } else if (article.label != null && article.label == "装备") {
                iterator.remove()
            }
        }
        return articles
    }
}