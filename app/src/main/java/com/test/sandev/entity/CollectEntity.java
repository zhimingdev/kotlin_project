package com.test.sandev.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CollectEntity implements Serializable {
    static final long serialVersionUID = -15515456L;
    @Id  //标识为id  可选 还有大量注解 可查询官方文档
    private String id;
    private String title;
    private String url;
    private String imageurl;
    private String count;
    private String userid;
    @Generated(hash = 499258125)
    public CollectEntity(String id, String title, String url, String imageurl,
            String count, String userid) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.imageurl = imageurl;
        this.count = count;
        this.userid = userid;
    }
    @Generated(hash = 1750617729)
    public CollectEntity() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImageurl() {
        return this.imageurl;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    public String getCount() {
        return this.count;
    }
    public void setCount(String count) {
        this.count = count;
    }
    public String getUserid() {
        return this.userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
}
