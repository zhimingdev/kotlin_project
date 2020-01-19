package com.test.sandev.module

class GuanZModule {


    /**
     * zjurl : http://file.caipiao365.com/lotteryER/010/01015109123761101381.jpg
     * zjname : 李玮锋
     * biaoshi : 足球名将
     * tablist : [{"history":"近9中8"}]
     * mingzhong : 100%命中率
     * desc : 近期5中4状态稳定！意甲焦点场次推荐！
     * day : 周五005
     * team : AC米兰 VS 乌迪内斯
     * tags : 意甲
     * time : 01-19 19:30
     * money : ￥55
     * fatime : 1天前发布
     */

    var zjurl: String? = null
    var zjname: String? = null
    var biaoshi: String? = null
    var mingzhong: String? = null
    var desc: String? = null
    var day: String? = null
    var team: String? = null
    var tags: String? = null
    var time: String? = null
    var money: String? = null
    var fatime: String? = null
    var tablist: List<TablistBean>? = null

    class TablistBean {
        /**
         * history : 近9中8
         */

        var history: String? = null
    }
}
