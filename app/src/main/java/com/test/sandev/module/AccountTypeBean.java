package com.test.sandev.module;

import java.io.Serializable;

public class AccountTypeBean implements Serializable {
    public int drawable;
    public String name;
    public int type;
    public int sort;

    public AccountTypeBean(int type, int sort, int drawable, String name) {
        this.drawable = drawable;
        this.name = name;
        this.type = type;
        this.sort = sort;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
