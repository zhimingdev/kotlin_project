package com.test.myapplication.module

class JifenModule {

    var coinCount : Int? = 0
    var level : Int? = 0
    var rank : Int? = 0
    var userId : Int? = 0
    var username : String? = null
    //收藏
    var collect : String? = (0..100).random().toString()
    //赞
    var starts : String? = (0..50).random().toString()
}