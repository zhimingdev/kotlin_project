package com.test.sandev.constans

import android.os.Environment

class UrlConstans {

    companion object{
        const val kefu_url : String = "http://mak.mdjiankang.com/Web/im.aspx?_=t&accountid=113931"

        val APK_SAVE_PATH = Environment.getExternalStorageDirectory().absolutePath + "/OB/update.apk"

        val BUNDLE_VIDEO_DATA = "video_data"


        //sp 存储的文件名
        val FILE_WATCH_HISTORY_NAME = "watch_history_file"   //观看记录
        //专家记录
        val FILE_PEOPLE = "PEOPLE_history_file"   //专家记录

        const val URL_Transfer = "https://api.dongqiudi.com/app/tabs/android/83.json" //转会
        const val URL_SUBJECT = "https://api.dongqiudi.com/app/tabs/classifications.json" //专题
        const val URL_CHINA = "https://api.dongqiudi.com/app/tabs/android/56.json" //中超


    }
}