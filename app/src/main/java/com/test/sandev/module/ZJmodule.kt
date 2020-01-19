package com.test.sandev.module

class ZJmodule {
    /**
     * host : {"paiming":"东部第14名","zj":"11-30","qs":"1败","name":"尼克斯"}
     * guest : {"paiming":"东部第1名","zj":"36-6","qs":"4连胜","name":"雄鹿"}
     */
    var host: HostBean? = null
    var guest: GuestBean? = null

    class HostBean {
        /**
         * paiming : 东部第14名
         * zj : 11-30
         * qs : 1败
         * name : 尼克斯
         */
        var paiming: String? = null
        var zj: String? = null
        var qs: String? = null
        var name: String? = null

    }

    class GuestBean {
        /**
         * paiming : 东部第1名
         * zj : 36-6
         * qs : 4连胜
         * name : 雄鹿
         */
        var paiming: String? = null
        var zj: String? = null
        var qs: String? = null
        var name: String? = null

    }
}