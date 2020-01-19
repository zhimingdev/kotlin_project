package com.test.sandev.module

class TeamModule {
    /**
     * host : {"win":"1","lose":"1","ping":"1","paiming":"9","jinqiu":"3","matcher":[{"time":"20/01/11","tags":"英超","team":"谢菲尔德联 VS 西汉姆联","score":"1 : 0","type":"已结束"},{"time":"20/01/03","tags":"英超","team":"谢菲尔德联 VS 利物浦","score":"2 : 0","type":"已结束"}]}
     * guest : {"win":"1","lose":"1","ping":"1","paiming":"14","jinqiu":"4","matcher":[{"time":"20/01/11","tags":"英超","team":"西汉姆联 VS 谢菲尔德联","score":"0 : 1","type":"已结束"},{"time":"20/01/03","tags":"英超","team":"西汉姆联 VS 伯恩茅斯","score":"4 : 0","type":"已结束"}]}
     */
    var host: HostBean? = null
    var guest: GuestBean? = null

    class HostBean {
        /**
         * win : 1
         * lose : 1
         * ping : 1
         * paiming : 9
         * jinqiu : 3
         * matcher : [{"time":"20/01/11","tags":"英超","team":"谢菲尔德联 VS 西汉姆联","score":"1 : 0","type":"已结束"},{"time":"20/01/03","tags":"英超","team":"谢菲尔德联 VS 利物浦","score":"2 : 0","type":"已结束"}]
         */
        var win: String? = null
        var lose: String? = null
        var ping: String? = null
        var paiming: String? = null
        var jinqiu: String? = null
        var matcher: List<MatcherBean>? = null

        class MatcherBean {
            /**
             * time : 20/01/11
             * tags : 英超
             * team : 谢菲尔德联 VS 西汉姆联
             * score : 1 : 0
             * type : 已结束
             */
            var time: String? = null
            var tags: String? = null
            var team: String? = null
            var score: String? = null
            var type: String? = null

        }
    }

    class GuestBean {
        /**
         * win : 1
         * lose : 1
         * ping : 1
         * paiming : 14
         * jinqiu : 4
         * matcher : [{"time":"20/01/11","tags":"英超","team":"西汉姆联 VS 谢菲尔德联","score":"0 : 1","type":"已结束"},{"time":"20/01/03","tags":"英超","team":"西汉姆联 VS 伯恩茅斯","score":"4 : 0","type":"已结束"}]
         */
        var win: String? = null
        var lose: String? = null
        var ping: String? = null
        var paiming: String? = null
        var jinqiu: String? = null
        var matcher: List<MatcherBeanX>? = null

        class MatcherBeanX {
            /**
             * time : 20/01/11
             * tags : 英超
             * team : 西汉姆联 VS 谢菲尔德联
             * score : 0 : 1
             * type : 已结束
             */
            var time: String? = null
            var tags: String? = null
            var team: String? = null
            var score: String? = null
            var type: String? = null

        }
    }
}