package com.test.sandev.module

class LQmodule{
    /**
     * type : 1
     * time : 20年1月21日 03:00
     * host : {"name":"活塞","url":"https://mat1.gtimg.com/sports/nba/logo/new/8.png","number":"-","one":"-","two":"-","three":"-","four":"-"}
     * guest : {"name":"奇才","url":"https://mat1.gtimg.com/sports/nba/logo/1602/27.png","number":"-","one":"-","two":"-","three":"-","four":"-"}
     */
    var type = 0
    var time: String? = null
    var host: HostBean? = null
    var guest: GuestBean? = null

    class HostBean{
        /**
         * name : 活塞
         * url : https://mat1.gtimg.com/sports/nba/logo/new/8.png
         * number : -
         * one : -
         * two : -
         * three : -
         * four : -
         */
        var name: String? = null
        var url: String? = null
        var number: String? = null
        var one: String? = null
        var two: String? = null
        var three: String? = null
        var four: String? = null
    }

    class GuestBean{
        /**
         * name : 奇才
         * url : https://mat1.gtimg.com/sports/nba/logo/1602/27.png
         * number : -
         * one : -
         * two : -
         * three : -
         * four : -
         */
        var name: String? = null
        var url: String? = null
        var number: String? = null
        var one: String? = null
        var two: String? = null
        var three: String? = null
        var four: String? = null

    }
}