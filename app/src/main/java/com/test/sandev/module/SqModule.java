package com.test.sandev.module;

import java.util.List;

public class SqModule {

    /**
     * username : Bing._HuangMa
     * headurl : http://thirdwx.qlogo.cn/mmopen/vi_32/iaDaCLaIicibfxw5Fcq3TfbFmyibbRjMdq6wSaiacRrz89EfBRNe4iajHmEgIwpcOGVCyvKEJYphZrYypa69l4tlPROQ/132
     * address : 苏州市
     * desc : 伯纳乌 皇马主场 一生所爱。
     * cotent : [{"imageurl":"http://img.kaiyanapp.com/18b8d27f8e658dccbae3256b0569327d.png"},{"imageurl":"http://img.kaiyanapp.com/ea242816b340985b8eef905d4b8812af.png"}]
     * loves : 26
     * discuss : 0
     * time : 2019-11-10
     */

    private String username;
    private String headurl;
    private String address;
    private String desc;
    private String loves;
    private int discuss;
    private String time;
    private List<CotentBean> cotent;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLoves() {
        return loves;
    }

    public void setLoves(String loves) {
        this.loves = loves;
    }

    public int getDiscuss() {
        return discuss;
    }

    public void setDiscuss(int discuss) {
        this.discuss = discuss;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<CotentBean> getCotent() {
        return cotent;
    }

    public void setCotent(List<CotentBean> cotent) {
        this.cotent = cotent;
    }

    public static class CotentBean {
        /**
         * imageurl : http://img.kaiyanapp.com/18b8d27f8e658dccbae3256b0569327d.png
         */

        private String imageurl;

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }
    }
}
