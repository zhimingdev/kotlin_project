package com.test.sandev.module

class LanqZhuModule {
    /**
     * zjurl : https://relottery.ws.126.net/user/20190306/7XbV9P.jpeg?imageView&thumbnail=60y60&quality=85
     * zjname : 李克
     * biaoshi : 篮球评论员
     * tablist : [{"history":"6连红"},{"history":"近3场中2场"}]
     * mingzhong : 命中67%
     */
    var zjurl: String? = null
    var zjname: String? = null
    var biaoshi: String? = null
    var mingzhong: String? = null
    var tablist: List<TablistBean>? = null

    class TablistBean {
        /**
         * history : 6连红
         */
        var history: String? = null

    }
}