package com.test.sandev

import android.app.Application
import android.database.sqlite.SQLiteDatabase

import com.test.sandev.greendao.DaoMaster
import com.test.sandev.greendao.DaoSession

class MyApplication : Application() {

    private var mDaoMaster: DaoMaster? = null
    var daoSession: DaoSession? = null
    private var mHelper: DaoMaster.DevOpenHelper? = null
    var db: SQLiteDatabase? = null

    override fun onCreate() {
        super.onCreate()
        instances = this
        initDataBase()
    }

    private fun initDataBase() {
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        mHelper = DaoMaster.DevOpenHelper(this, "collect-db", null)
        db = mHelper!!.writableDatabase
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = DaoMaster(db)
        daoSession = mDaoMaster!!.newSession()
    }

    companion object {
        lateinit var instances: MyApplication
    }
}
