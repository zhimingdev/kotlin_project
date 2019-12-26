package com.test.sandev.module

class RecordModule {

    /**
     * hostinfo : {"hoet_image":"http: //liansai.500.com/static/soccerdata/images/TeamPic/teamsignnew_653.png","hoet_name":"巴塞罗那","host_count":"4","hostInfo":{"onelist":[{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_319.png"}],"twolist":[{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_136.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_117.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_1422.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_131.png"}],"threelist":[{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_3187.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_129.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_42398.png"}],"fourlist":[{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_124.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_3239.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_1216.png"}]}}
     * guestinfo : {"guest_image":"http: //liansai.500.com/static/soccerdata/images/TeamPic/teamsignnew_542.png","guest_name":"阿拉维斯","guest_count":"1","guestInfo":{"onelist":[{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5712.png"}],"twolist":[{"headurl":"http://www.500cache.com/liansai/images/notfound.png"},{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_4931.png"},{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5667.png"},{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5667.png"}],"threelist":[{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5463.png"},{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5755.png"},{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5777.png"},{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_8333.png"},{"headurl":"http://www.500cache.com/liansai/images/notfound.png"}],"fourlist":[{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_9144.png"}]}}
     * playinfo : [{"hostimg":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_124.png","hostaddress":"前锋","hostname":"梅西","hostcoint":"1","guestimg":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_9144.png","guestaddress":"前锋","guestname":"卢卡斯","guestcoint":"0"},{"hostimg":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_3239.png","hostaddress":"前锋","hostname":"苏亚雷斯","hostcoint":"1","guestimg":"https://www.500cache.com/liansai/images/notfound.png","guestaddress":"中场","guestname":"哈维尔·穆尼","guestcoint":"0"},{"hostimg":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_1216.png","hostaddress":"前锋","hostname":"格列兹曼","hostcoint":"1","guestimg":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5463.png","guestaddress":"中场","guestname":"比达尔","guestcoint":"0"},{"hostimg":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_3187.png","hostaddress":"中场","hostname":"比达尔","hostcoint":"1","guestimg":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5755.png","guestaddress":"中场","guestname":"列拉","guestcoint":"1"},{"hostimg":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_129.png","hostaddress":"中场","hostname":"布斯克茨","hostcoint":"0","guestimg":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5777.png","guestaddress":"中场","guestname":"加西亚","guestcoint":"0"}]
     */

    var hostinfo: HostinfoBean? = null
    var guestinfo: GuestinfoBean? = null
    var playinfo: List<PlayinfoBean>? = null
    var tags : String? = null

    class HostinfoBean {
        /**
         * hoet_image : http: //liansai.500.com/static/soccerdata/images/TeamPic/teamsignnew_653.png
         * hoet_name : 巴塞罗那
         * host_count : 4
         * hostInfo : {"onelist":[{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_319.png"}],"twolist":[{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_136.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_117.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_1422.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_131.png"}],"threelist":[{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_3187.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_129.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_42398.png"}],"fourlist":[{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_124.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_3239.png"},{"headurl":"http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_1216.png"}]}
         */

        var hoet_image: String? = null
        var hoet_name: String? = null
        var host_count: String? = null
        var hostInfo: HostInfoBean? = null

        class HostInfoBean {
            var onelist: List<OnelistBean>? = null
            var twolist: List<TwolistBean>? = null
            var threelist: List<ThreelistBean>? = null
            var fourlist: List<FourlistBean>? = null

            class OnelistBean {
                /**
                 * headurl : http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_319.png
                 */

                var headurl: String? = null
                var name : String? = null
            }

            class TwolistBean {
                /**
                 * headurl : http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_136.png
                 */

                var headurl: String? = null
                var name : String? = null
            }

            class ThreelistBean {
                /**
                 * headurl : http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_3187.png
                 */

                var headurl: String? = null
                var name : String? = null
            }

            class FourlistBean {
                /**
                 * headurl : http: //liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_124.png
                 */

                var headurl: String? = null
                var name : String? = null
            }
        }
    }

    class GuestinfoBean {
        /**
         * guest_image : http: //liansai.500.com/static/soccerdata/images/TeamPic/teamsignnew_542.png
         * guest_name : 阿拉维斯
         * guest_count : 1
         * guestInfo : {"onelist":[{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5712.png"}],"twolist":[{"headurl":"http://www.500cache.com/liansai/images/notfound.png"},{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_4931.png"},{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5667.png"},{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5667.png"}],"threelist":[{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5463.png"},{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5755.png"},{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5777.png"},{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_8333.png"},{"headurl":"http://www.500cache.com/liansai/images/notfound.png"}],"fourlist":[{"headurl":"http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_9144.png"}]}
         */

        var guest_image: String? = null
        var guest_name: String? = null
        var guest_count: String? = null
        var guestInfo: GuestInfoBean? = null

        class GuestInfoBean {
            var onelist: List<OnelistBeanX>? = null
            var twolist: List<TwolistBeanX>? = null
            var threelist: List<ThreelistBeanX>? = null
            var fourlist: List<FourlistBeanX>? = null

            class OnelistBeanX {
                /**
                 * headurl : http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5712.png
                 */

                var headurl: String? = null
                var name : String? = null
            }

            class TwolistBeanX {
                /**
                 * headurl : http://www.500cache.com/liansai/images/notfound.png
                 */

                var headurl: String? = null
                var name : String? = null
            }

            class ThreelistBeanX {
                /**
                 * headurl : http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_5463.png
                 */

                var headurl: String? = null
                var name : String? = null
            }

            class FourlistBeanX {
                /**
                 * headurl : http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_9144.png
                 */

                var headurl: String? = null
                var name : String? = null
            }
        }
    }

    class PlayinfoBean {
        /**
         * hostimg : http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_124.png
         * hostaddress : 前锋
         * hostname : 梅西
         * hostcoint : 1
         * guestimg : http://liansai.500.com/static/soccerdata/images/CharacterPic/characterpic1_9144.png
         * guestaddress : 前锋
         * guestname : 卢卡斯
         * guestcoint : 0
         */

        var hostimg: String? = null
        var hostaddress: String? = null
        var hostname: String? = null
        var hostcoint: String? = null
        var guestimg: String? = null
        var guestaddress: String? = null
        var guestname: String? = null
        var guestcoint: String? = null
    }
}
