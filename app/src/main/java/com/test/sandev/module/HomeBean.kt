package com.test.sandev.module

class HomeBean {

    /**
     * customurl : http://www.baidu.url
     * datas : [{"title":"12.14精选8场赛事推荐：布雷西亚本场有望拿下主场首胜！","descriptionurl":"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_10031343011114673800%22%7D&n_type=1&p_from=4","time":"2019-12-14 09:57","imageurl":"https://pics7.baidu.com/feed/5243fbf2b21193139248ab449857e9d290238db4.jpeg?token=edca02f123d93f5193a04363968119aa&s=ADA46495400268EC52A10D49030080F0"},{"title":"2019/09/09-欧洲杯赛事推荐文章","descriptionurl":"http://baijiahao.baidu.com/s?id=1644111041614061543&wfr=spider&for=pc","time":"2019-12-09 18.23","imageurl":"http://pics2.baidu.com/feed/e7cd7b899e510fb3194a4dd28e78a290d0430cf7.jpeg?token=1085cff678193e1f024500c5a7970858&s=B02AE0B10CD364D05FA1BA1E030070D5"},{"title":"各有千秋？德布劳内称斯特林，阿圭罗接球习惯截然不同","descriptionurl":"https://m.24zbw.com/tysp/tyjj/1024/1024/2491127.html","time":"2019-12-07 15:03","imageurl":"https://image.24zbw.com/article/D4AD782D-A51B-4A9B-81C7-F317F7EB9471"},{"title":"百年恩怨！荷甲榜首之争激情上演","descriptionurl":"https://weibo.com/ttarticle/p/show?id=2309404355902462365133","time":"2019-12-06 15:31","imageurl":"https://wx2.sinaimg.cn/large/b7cd25aegy1g1lxjqdj4jj20rs0fme2q.jpg"},{"title":"默西塞德德比火爆进行，红军坐镇安菲尔德大胜太妃糖","descriptionurl":"https://m.24zbw.com/tysp/tyjj/1024/1024/2490944.html","time":"2019-12-05 10:52","imageurl":"https://image.24zbw.com/article/554AE7FB-544E-46F1-8C1E-14D67C3880D2"},{"title":"利物浦两连官宣！克洛普＋队副续约红军，杰拉特同样宣布续约","descriptionurl":"https://new.qq.com/omn/20191214/20191214A0F8CB00.html","time":"2019-12-14 13:55","imageurl":"https://inews.gtimg.com/newsapp_bt/0/10974839006/1000"},{"title":"逆转秘诀！渣叔直言利物浦拥有钢铁意志","descriptionurl":"https://m.24zbw.com/tysp/tyjj/1024/1024/2490021.html","time":"2019-11-22 15:05","imageurl":"https://image.24zbw.com/article/FB7D22F5-184D-412A-AC66-26CB19BF1CC0"}]
     */

    var customurl: String? = null
    var datas: List<DatasBean>? = null

    class DatasBean {
        /**
         * title : 12.14精选8场赛事推荐：布雷西亚本场有望拿下主场首胜！
         * descriptionurl : https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_10031343011114673800%22%7D&n_type=1&p_from=4
         * time : 2019-12-14 09:57
         * imageurl : https://pics7.baidu.com/feed/5243fbf2b21193139248ab449857e9d290238db4.jpeg?token=edca02f123d93f5193a04363968119aa&s=ADA46495400268EC52A10D49030080F0
         */

        var title: String? = null
        var descriptionurl: String? = null
        var time: String? = null
        var imageurl: String? = null
        var count :String? = null
        var tags : String? = null
    }
}
