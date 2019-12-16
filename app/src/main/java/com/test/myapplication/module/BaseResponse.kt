package com.test.myapplication.module

class BaseResponse<T> {


    /**
     * code : 200
     * msg : 请求成功
     * data : {"name":"杭三","level":"45"}
     */

    var code: Int = 0
    var msg: String? = null
    var data: T? = null
    var errorCode : Int = 0
    var errorMsg : String? = null

}
