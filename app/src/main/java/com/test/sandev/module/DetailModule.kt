package com.test.sandev.module

class DetailModule {
    /**
     * tags : 2中1
     * titles : 热火vs马刺 让分 ＋大小分
     * redords : [{"team":"[竞篮] NBA: 热火 102:107 马刺","time":"01/20 04:00"}]
     * cjsj : 19小时前发布
     */
    var tags: String? = null
    var titles: String? = null
    var cjsj: String? = null
    var redords: List<RedordsBean>? = null

    class RedordsBean {
        /**
         * team : [竞篮] NBA: 热火 102:107 马刺
         * time : 01/20 04:00
         */
        var team: String? = null
        var time: String? = null

    }
}