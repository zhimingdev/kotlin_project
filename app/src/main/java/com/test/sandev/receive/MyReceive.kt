package com.test.sandev.receive

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.JPushInterface
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import com.blankj.utilcode.util.AppUtils
import com.test.sandev.R
import com.test.sandev.ui.activity.LoginActivity
import com.test.sandev.ui.activity.SplashActivity

class MyReceive : JPushMessageReceiver() {
    private val TAG = "MyReceiver"

    private var nm: NotificationManager? = null

    override fun onRegister(p0: Context?, p1: String?) {
        super.onRegister(p0, p1)
    }

    //接收到通知
    override fun onNotifyMessageArrived(p0: Context?, p1: NotificationMessage?) {
        super.onNotifyMessageArrived(p0, p1)
    }

    //打开通知
    override fun onNotifyMessageOpened(context: Context?, customMessage: NotificationMessage?) {
        super.onNotifyMessageOpened(context, customMessage)
        var intent = Intent(context,SplashActivity::class.java)
        context!!.startActivity(intent)
    }

    //自定义消息
    override fun onMessage(context: Context?, customMessage: CustomMessage?) {
        super.onMessage(context, customMessage)
        processCustomMessage(context!!, customMessage!!.extra)
    }

    private fun receivingNotification(context: Context, bundle: Bundle) {
        val title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE)
        Log.d(TAG, " title : $title")
        val message = bundle.getString(JPushInterface.EXTRA_ALERT)
        Log.d(TAG, "message : $message")
        val extras = bundle.getString(JPushInterface.EXTRA_EXTRA)
        Log.d(TAG, "extras : $extras")
    }

    private fun processCustomMessage(context: Context, message: String) {
        val channelID = "1"
        val channelName = "channel_name"
        // 跳转的Activity
        val intent = Intent(context, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        //适配安卓8.0的消息渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(context, channelID)
        notification.setAutoCancel(true)
                .setContentText(message)
                .setContentTitle(AppUtils.getAppName())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)

        notificationManager.notify((System.currentTimeMillis() / 1000).toInt(), notification.build())
    }

}