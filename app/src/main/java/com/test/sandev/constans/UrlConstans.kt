package com.test.sandev.constans

import android.os.Environment

class UrlConstans {

    companion object{
        const val kefu_url : String = "http://mak.mdjiankang.com/Web/im.aspx?_=t&accountid=113931"

        val APK_SAVE_PATH = Environment.getExternalStorageDirectory().absolutePath + "/OB/update.apk"
    }
}