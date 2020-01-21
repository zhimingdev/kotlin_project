package com.test.sandev.api
import com.test.sandev.module.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("woshishui")
    fun getData(@QueryMap map:Map<String,String> ):Call<Void>

    @GET("v1/home/getBanner")
    fun getBanner():Observable<BaseResponse<List<BannerBean>>>

    @GET("v1/home/getData")
    fun getHomeData():Observable<BaseResponse<HomeBean>>

    @FormUrlEncoded
    @POST("user/register")
    fun getUserInfo(@FieldMap map:Map<String,String>) : Observable<BaseResponse<UserModule>>

    @FormUrlEncoded
    @POST("user/login")
    fun getUserLogin(@FieldMap map:Map<String,String>) : Observable<BaseResponse<UserModule>>

    @GET("v1/video/videolist")
    fun getVideoData() : Observable<BaseResponse<List<VideoModule>>>

    @GET("lg/coin/userinfo/json")
    fun getUserJiFen(@QueryMap map:Map<String,Int>) : Observable<BaseResponse<MineJifenModule>>

    @GET("v1/mine/url")
    fun getMineUrl() : Observable<BaseResponse<MineModule>>

    @GET("v1/home/update")
    fun getUpdate() : Observable<BaseResponse<UpdateModule>>

    @GET("v1/home/upsatenew")
    fun getNewUpdate() : Observable<BaseResponse<UpdateModule>>

    @GET("v1/mine/isShow")
    fun getPack() : Observable<BaseResponse<PackModule>>

    @GET("v1/home/getSquare")
    fun getSqData() : Observable<BaseResponse<List<SqModule>>>

    @GET("v1/vv_home/getInfo")
    fun getAaData() : Observable<BaseResponse<HomeBean>>


    @GET("v1/vv_video/getV")
    fun getAvInfo() :Observable<BaseResponse<List<VideoModule>>>

    @GET("v1/home/vv_get")
    fun getVV() :Observable<BaseResponse<List<BannerBean>>>

    @FormUrlEncoded
    @POST("v1/record/getrecorddetail")
    fun getRecordDetail(@FieldMap map : Map<String,Int>):Observable<BaseResponse<RecordModule>>

    @GET("v1/home/matchrecord")
    fun getMatchRecord() : Observable<BaseResponse<List<MatchModule>>>

    @GET("v1/home/getOpenUrl")
    fun getOpenUrl() : Observable<BaseResponse<OpenUrlModule>>


    @GET("/coin/rank/{page}/json")
    fun getJFData(@Path("page") page : String) : Observable<BaseResponse<JiFenModule>>

    @GET("v1/home/hotMatch")
    fun getHot() :Observable<BaseResponse<List<HotBean>>>

    @FormUrlEncoded
    @POST("v2/hotmather/teaminfo")
    fun getTeam(@FieldMap map : Map<String,Int>) : Observable<BaseResponse<TeamModule>>

    @GET("v2/hotmatch/textalive")
    fun getText() : Observable<BaseResponse<List<TextModule>>>

    @FormUrlEncoded
    @POST("v2/hotmatch/totaldata")
    fun getInfo(@FieldMap map : Map<String,Int>) : Observable<BaseResponse<List<InfoModule>>>

    @GET("v2/home/yc")
    fun getYC() : Observable<BaseResponse<List<TPModule>>>

    @GET("v2/homt/yj")
    fun getYJ() : Observable<BaseResponse<List<TPModule>>>

    @GET("v2/homt/xj")
    fun getXJ() : Observable<BaseResponse<List<TPModule>>>

    @GET("v2/home/og")
    fun getOG() : Observable<BaseResponse<List<TPModule>>>

    @GET("v2/home/dj")
    fun getDJ() : Observable<BaseResponse<List<TPModule>>>

    @GET("v2/home/nba")
    fun getNBA() : Observable<BaseResponse<List<TPModule>>>

    @GET("v2/natcher/lq")
    fun getLQ() : Observable<BaseResponse<List<LQmodule>>>

    @FormUrlEncoded
    @POST("v2/bas/detail")
    fun getzj(@FieldMap map : Map<String,Int>) : Observable<BaseResponse<ZJmodule>>

    @GET("v3/stadium/api/stadiums/stadiumItems")
    fun getGuan() : Observable<BaseResponse<GuanModule>>

    @GET("v2/footbar/data")
    fun getZUguanzhu() : Observable<BaseResponse<List<GuanZModule>>>

    @GET("v2/bashet/people")
    fun getLQzhuajia() : Observable<BaseResponse<List<LanqZhuModule>>>

    @GET("v2/people/detail")
    fun getDetailpeople() : Observable<BaseResponse<List<DetailModule>>>

    /**
     * 热门搜索词
     */
    @GET("v3/queries/hot")
    fun getHotWord():Observable<ArrayList<String>>

    /**
     * 获取搜索信息
     */
    @GET("v1/search?&num=10&start=10")
    fun getSearchData(@Query("query") query :String) : Observable<HomeNewBean.Issue>

    /**
     * 获取更多的 Issue
     */
    @GET
    fun getIssueData(@Url url: String): Observable<HomeNewBean.Issue>

    /**
     * 根据item id获取相关视频
     */
    @GET("v4/video/related?")
    fun getRelatedData(@Query("id") id: Long): Observable<HomeNewBean.Issue>

}