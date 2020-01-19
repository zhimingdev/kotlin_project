package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.test.sandev.R
import com.test.sandev.adapter.MatchRecordAdapter
import com.test.sandev.adapter.VideoAdapter
import com.test.sandev.adapter.holder.VideoViewHolder
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.*
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_common.*

class CommonFragment(private var number : Int) : BaseFragment() {

    val netWork by lazy { NetWork() }

    var data : List<VideoModule>? = listOf()

    companion object {
        fun getInstanca(type : Int) : CommonFragment {
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
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_common,null)
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
            it.finishLoadMore()
            it.finishLoadMoreWithNoMoreData()
        }
    }

    private fun loadData() {
        if (number == 0) {
            netWork.getApi(Api::class.java).getMatchRecord()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : ApiBaseResponse<List<MatchModule>>(activity!!) {
                        override fun onSuccess(t: List<MatchModule>?) {
                            var adapter : MatchRecordAdapter = MatchRecordAdapter(activity!!,t)
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
        }else{
            getDtat()
        }
    }

    private fun getDtat() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.setRecyclerListener {
            val niceVideoPlayer = (it as VideoViewHolder).mVideoPlayer
            if (niceVideoPlayer === NiceVideoPlayerManager.instance().currentNiceVideoPlayer) {
                NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
            }
        }
        netWork.getApi(Api::class.java).getVideoData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :ApiBaseResponse<List<VideoModule>>(activity!!) {
                    override fun onSuccess(t: List<VideoModule>?) {
                        data = t
                        val adapter = VideoAdapter(context!!, data)
                        recyclerView.adapter = adapter
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
//        netWork.getApi(Api::class.java).getAvInfo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object :ApiBaseResponse<List<VideoModule>>(activity!!) {
//                    override fun onSuccess(t: List<VideoModule>?) {
//                        data = t
//                        val adapter = VideoAdapter(context!!, data)
//                        recyclerView.adapter = adapter
//                    }
//
//                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
//                    }
//
//                    override fun onFail(e: ApiError) {
//                    }
//
//                })
    }


    override fun onStop() {
        super.onStop()
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
        }
    }
}