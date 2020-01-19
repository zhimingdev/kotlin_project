package com.test.sandev.ui.activity

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.*
import com.amap.api.services.core.AMapException
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.test.sandev.R
import java.util.*

class MapActivity : AppCompatActivity(), AMapLocationListener, OnPoiSearchListener {
    //声明mlocationClient对象
    var mlocationClient: AMapLocationClient? = null
    //声明mLocationOption对象
    var mLocationOption: AMapLocationClientOption? = null
    var myLocationStyle: MyLocationStyle? = null
    private var mapView: MapView? = null
    var aMap: AMap? = null
    private var recyclerView: RecyclerView? = null
    private var pois: ArrayList<PoiItem>? = null
    private var adapter: MyAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        mapView = findViewById<View>(R.id.map) as MapView
        recyclerView = findViewById<View>(R.id.rv_map) as RecyclerView
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView!!.layoutManager = linearLayoutManager
        adapter = MyAdapter()
        recyclerView!!.adapter = adapter
        initData()
        mapView!!.onCreate(savedInstanceState)
    }

    protected fun initData() {
        if (aMap == null) {
            aMap = mapView!!.map
        }
        aMap!!.moveCamera(CameraUpdateFactory.zoomTo(19.0f))
        myLocationStyle = MyLocationStyle() //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle!!.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle!!.strokeColor(Color.argb(0, 0, 0, 0)) // 设置圆形的边框颜色
        myLocationStyle!!.radiusFillColor(Color.argb(0, 0, 0, 0)) // 设置圆形的填充颜色
        aMap!!.myLocationStyle = myLocationStyle //设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap!!.isMyLocationEnabled = true // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap!!.setMyLocationType(AMap.LOCATION_TYPE_LOCATE)
        mlocationClient = AMapLocationClient(this)
        //初始化定位参数
        mLocationOption = AMapLocationClientOption()
        //设置定位监听
        mlocationClient!!.setLocationListener(this)
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption!!.interval = 2000
        //设置定位参数
        mlocationClient!!.setLocationOption(mLocationOption)
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        aMap!!.setOnMarkerClickListener {
            it.title = it.title
            it.showInfoWindow()
            true
        }
        mlocationClient!!.startLocation()

        adapter!!.setOnItemClickListener {adapter, view, position ->
            intent.putExtra("result",pois!![position].title)
            setResult(1,intent)
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        intent.putExtra("result","")
        setResult(1,intent)
        finish()
    }

    override fun onLocationChanged(amapLocation: AMapLocation) {
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) { //定位成功回调信息，设置相关消息
                val latitude = amapLocation.latitude //获取纬度
                val longitude = amapLocation.longitude //获取经度
                search(latitude, longitude)
            } else { //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.errorCode + ", errInfo:"
                        + amapLocation.errorInfo)
            }
        }
    }

    private fun search(latitude: Double, longitude: Double) {
        val query = PoiSearch.Query("", "生活服务", "")
        //keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.pageSize = 50 // 设置每页最多返回多少条poiitem
        query.pageNum = 0 //设置查询页码
        val poiSearch = PoiSearch(this, query)
        poiSearch.bound = PoiSearch.SearchBound(LatLonPoint(latitude, longitude), 80000)
        poiSearch.setOnPoiSearchListener(this)
        poiSearch.searchPOIAsyn()
    }

    override fun onPoiSearched(poiResult: PoiResult, rCode: Int) {
        var list: MutableList<MultiPointItem> = mutableListOf()
        list.clear()
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            pois = poiResult.pois
            adapter!!.setNewData(pois)
            for (i in 0 until pois!!.size) {
                var latLonPoint = pois!![i].latLonPoint
                list.add(MultiPointItem(LatLng(latLonPoint.latitude, latLonPoint.longitude)))
            }
            showResultOnMap(list)
        }
    }

    private fun showResultOnMap(list: MutableList<MultiPointItem>) {
        var overlayOptions = MultiPointOverlayOptions()
        overlayOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_marker))
        overlayOptions.anchor(0.5f, 0.5f)
        var multiPointOverlay = aMap!!.addMultiPointOverlay(overlayOptions)
        multiPointOverlay.setItems(list)
        aMap!!.setOnMultiPointClickListener { false }
    }

    override fun onPoiItemSearched(poiItem: PoiItem, i: Int) {}

    inner class MyAdapter : BaseQuickAdapter<PoiItem, BaseViewHolder>(R.layout.rv_item, pois) {
        override fun convert(helper: BaseViewHolder, item: PoiItem) {
            helper.setText(R.id.tv_map_item, item.title)
        }
    }
}