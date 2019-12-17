package com.test.sandev.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.sandev.R
import com.test.sandev.adapter.VideoAdapter
import com.test.sandev.adapter.holder.VideoViewHolder
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import com.test.sandev.module.VideoModule
import com.test.sandev.utils.ApiBaseResponse
import com.test.sandev.utils.NetWork
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_vbang.*

class VBangFragment : BaseFragment() {

    var data : List<VideoModule>? = listOf()
    val network by lazy { NetWork() }

    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_vbang,null)
        return view
    }

    private fun getDtat() {
        network.getApi(Api::class.java).getVideoData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :ApiBaseResponse<List<VideoModule>>(activity!!) {
                    override fun onSuccess(t: List<VideoModule>?) {
                        data = t
                        val adapter = VideoAdapter(context!!, data)
                        rv_view.adapter = adapter
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    override fun initDate() {
        getDtat()
        rv_view.layoutManager = LinearLayoutManager(activity)
        rv_view.setHasFixedSize(true)
        rv_view.setRecyclerListener {
            val niceVideoPlayer = (it as VideoViewHolder).mVideoPlayer
            if (niceVideoPlayer === NiceVideoPlayerManager.instance().currentNiceVideoPlayer) {
                NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
            }
        }
    }

    override fun initListenter() {

    }

    override fun onStop() {
        super.onStop()
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
        }
    }
}