package com.test.sandev.module

class JiFenModule {
    /**
     * curPage : 1
     * datas : [{"coinCount":6236,"level":63,"rank":1,"userId":20382,"username":"g**eii"},{"coinCount":6032,"level":61,"rank":2,"userId":27535,"username":"1**08491840"},{"coinCount":5657,"level":57,"rank":3,"userId":3559,"username":"A**ilEyon"},{"coinCount":4534,"level":46,"rank":4,"userId":1260,"username":"于**家的吴蜀黍"},{"coinCount":4500,"level":45,"rank":5,"userId":2,"username":"x**oyang"},{"coinCount":4156,"level":42,"rank":6,"userId":29303,"username":"深**士"},{"coinCount":4138,"level":42,"rank":7,"userId":28694,"username":"c**ng0218"},{"coinCount":3887,"level":39,"rank":8,"userId":9621,"username":"S**24n"},{"coinCount":3871,"level":39,"rank":9,"userId":1534,"username":"j**gbin"},{"coinCount":3705,"level":38,"rank":10,"userId":2068,"username":"i**Cola"},{"coinCount":3670,"level":37,"rank":11,"userId":863,"username":"m**qitian"},{"coinCount":3632,"level":37,"rank":12,"userId":7710,"username":"i**Cola7"},{"coinCount":3600,"level":36,"rank":13,"userId":3753,"username":"S**phenCurry"},{"coinCount":3585,"level":36,"rank":14,"userId":833,"username":"w**lwaywang6"},{"coinCount":3535,"level":36,"rank":15,"userId":7590,"username":"陈**啦啦啦"},{"coinCount":3485,"level":35,"rank":16,"userId":15603,"username":"r**eryxx"},{"coinCount":3471,"level":35,"rank":17,"userId":7809,"username":"1**23822235"},{"coinCount":3445,"level":35,"rank":18,"userId":23244,"username":"a**ian"},{"coinCount":3440,"level":35,"rank":19,"userId":1871,"username":"l**shifu"},{"coinCount":3435,"level":35,"rank":20,"userId":25793,"username":"F**_2014"},{"coinCount":3435,"level":35,"rank":21,"userId":28607,"username":"S**Brother"},{"coinCount":3390,"level":34,"rank":22,"userId":14829,"username":"l**changwen"},{"coinCount":3380,"level":34,"rank":23,"userId":6142,"username":"c**huah"},{"coinCount":3380,"level":34,"rank":24,"userId":14032,"username":"M**eor"},{"coinCount":3354,"level":34,"rank":25,"userId":7891,"username":"h**zkp"},{"coinCount":3353,"level":34,"rank":26,"userId":7541,"username":"l**64301766"},{"coinCount":3329,"level":34,"rank":27,"userId":12351,"username":"w**igeny"},{"coinCount":3305,"level":34,"rank":28,"userId":27,"username":"y**ochoo"},{"coinCount":3305,"level":34,"rank":29,"userId":25419,"username":"蔡**打篮球"},{"coinCount":3305,"level":34,"rank":30,"userId":2160,"username":"R**iner"}]
     * offset : 0
     * over : false
     * pageCount : 487
     * size : 30
     * total : 14581
     */
    var curPage = 0
    var offset = 0
    var isOver = false
    var pageCount = 0
    var size = 0
    var total = 0
    var datas: List<DatasBean>? = null

    class DatasBean {
        /**
         * coinCount : 6236
         * level : 63
         * rank : 1
         * userId : 20382
         * username : g**eii
         */
        var coinCount = 0
        var level = 0
        var rank = 0
        var userId = 0
        var username: String? = null

    }
}