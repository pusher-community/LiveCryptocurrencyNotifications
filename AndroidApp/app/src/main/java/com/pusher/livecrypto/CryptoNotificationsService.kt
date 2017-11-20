package com.pusher.livecrypto

import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.support.v7.app.NotificationCompat
import android.widget.RemoteViews

import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.NotificationTarget
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CryptoNotificationsService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        val data = remoteMessage!!.data
        val graphUrl = data["graph_url"]
        val currentPrice = data["currentPrice"]
        val openPrice = data["openPrice"]
        val currencyPair = data["currencyPair"]

        val notificationViews = RemoteViews(applicationContext.packageName, R.layout.notification_view)

        val difference = java.lang.Double.parseDouble(currentPrice) - java.lang.Double.parseDouble(openPrice)
        notificationViews.setTextViewText(R.id.price_text, String.format("%s: %s", currencyPair, currentPrice))

        var arrow = "↑"
        if (difference > 0) {
            notificationViews.setTextColor(R.id.price_difference_text, getColor(R.color.green))
        } else if (difference == 0.0) {
            notificationViews.setTextColor(R.id.price_difference_text, getColor(R.color.black))
            arrow = ""
        } else {
            notificationViews.setTextColor(R.id.price_difference_text, getColor(R.color.red))
            arrow = "↓"
        }

        notificationViews.setTextViewText(R.id.price_difference_text, String.format("%.2f %s", difference, arrow))

        val notificationId = 1
        val notification = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_show_chart_black_24px)
                .setCustomBigContentView(notificationViews)
                .build()


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)

        val notificationTarget = NotificationTarget(
                this,
                R.id.chart_img,
                notificationViews,
                notification,
                1)

        val uri = Uri.parse(graphUrl)
        Glide.get(applicationContext).clearDiskCache()

        Handler(Looper.getMainLooper()).post {
            Glide.with(applicationContext)
                    .asBitmap()
                    .load(uri)
                    .into(notificationTarget)
        }
    }
}
