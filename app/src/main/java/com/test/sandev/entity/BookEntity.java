package com.test.sandev.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BookEntity {
    public static final int BOOK_TYPE_PAY_OUT = 0x00;
    public static final int BOOK_TYPE_PAY_IN = 0x01;
    @Id(autoincrement = true)
    private Long bookId;

    //账本类型 0:支出 1:收入
    private int bookType;
    //账本分类
    private int bookSort;
    private int year;
    private int month;
    private int day;
    private long time;
    private String bookNote;
    private double amount;
    @Generated(hash = 2071114367)
    public BookEntity(Long bookId, int bookType, int bookSort, int year, int month,
            int day, long time, String bookNote, double amount) {
        this.bookId = bookId;
        this.bookType = bookType;
        this.bookSort = bookSort;
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
        this.bookNote = bookNote;
        this.amount = amount;
    }
    @Generated(hash = 1373651409)
    public BookEntity() {
    }
    public Long getBookId() {
        return this.bookId;
    }
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    public int getBookType() {
        return this.bookType;
    }
    public void setBookType(int bookType) {
        this.bookType = bookType;
    }
    public int getBookSort() {
        return this.bookSort;
    }
    public void setBookSort(int bookSort) {
        this.bookSort = bookSort;
    }
    public int getYear() {
        return this.year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return this.month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getDay() {
        return this.day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getBookNote() {
        return this.bookNote;
    }
    public void setBookNote(String bookNote) {
        this.bookNote = bookNote;
    }
    public double getAmount() {
        return this.amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
