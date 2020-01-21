package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.test.sandev.R
import com.test.sandev.adapter.CategoryDetailAdapter
import com.test.sandev.adapter.LeagueAdapter
import com.test.sandev.adapter.MatchRecordAdapter
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.constans.UrlConstans
import com.test.sandev.module.*
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_common.*

class CommonFragment(private var number: Int) : BaseFragment(){

    val netWork by lazy { NetWork() }
    private var nextPageUrl: String? = null
    private var itemList = ArrayList<HomeNewBean.Issue.Item>()
    private val mResultAdapter by lazy { CategoryDetailAdapter(activity!!, itemList, R.layout.item_category_detail) }

    companion object {
        fun getInstanca(type: Int): CommonFragment {
            return CommonFragment(type)
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
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_common, null)
        return view
    }

    override fun initSandevDate() {
        var linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        loadData()
    }

    override fun initSandevListenter() {
        srl.setOnRefreshListener {
            loadData()
            it.finishRefresh()
        }

        srl.setOnLoadMoreListener {
            loadMoreData()
            it.finishLoadMore()
        }
    }

    private fun loadData() {
        if (number == 0) {
            netWork.getApi(Api::class.java).getMatchRecord()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : ApiBaseResponse<List<MatchModule>>(activity!!) {
                        override fun onSuccess(t: List<MatchModule>?) {
                            var adapter: MatchRecordAdapter = MatchRecordAdapter(activity!!, t)
                            recyclerView.adapter = adapter
                        }

                        override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                        }

                        override fun onFail(e: ApiError) {
                        }

                    })
            netWork.getApi(Api::class.java).getAaData()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(object : ApiBaseResponse<HomeBean>(activity!!) {
//                        override fun onSuccess(t: HomeBean?) {
//                            if (homeAdapter == null) {
//                                homeAdapter = HomeAdapter(context,t!!.datas)
//                            }
//                            recyclerView.adapter = homeAdapter
//                        }
//
//                        override fun onCodeError(tBaseReponse: BaseResponse<*>) {
//                        }
//
//                        override fun onFail(e: ApiError) {
//                        }
//
//                    })
        } else if (number == 1) {
            getDtat("精选")
        } else if (number == 2) {
            getDtat("足球")
        } else if (number == 3) {
            getDtat("足球赛")
        } else if (number == 4) {
            getDtat("足球视频")
        } else if (number == 5) {
            getDtat("足球被")
        } else if (number == 6) {
            getDtat("足球技巧")
        } else if (number == 9) {
            getDtat("篮球精选")
        } else if (number == 10) {
            getDtat("NBA")
        } else if (number == 11) {
            getDtat("篮球视频")
        }

    }

    private fun getDtat(s: String) {
        netWork.getBaseApi(Api::class.java).getSearchData(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeNewBean.Issue> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(issue: HomeNewBean.Issue) {
                        recyclerView.adapter = mResultAdapter
                        if (issue.count > 0 && issue.itemList.size > 0) {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        }
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }

    private fun setSearchResult(issue: HomeNewBean.Issue) {
        itemList = issue.itemList
        mResultAdapter.addData(issue.itemList)
    }

    private fun loadMoreData() {
        netWork.getBaseApi(Api::class.java).getIssueData(nextPageUrl!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeNewBean.Issue> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(issue: HomeNewBean.Issue) {
                        nextPageUrl = issue.nextPageUrl
                        setSearchResult(issue)
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }
}