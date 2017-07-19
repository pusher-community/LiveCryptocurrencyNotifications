package com.pusher.livecrypto;

import android.app.Notification;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class CryptoNotificationsService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();
        String graphUrl = data.get("graph_url");
        String currentPrice = data.get("currentPrice");
        String openPrice = data.get("openPrice");
        String currencyPair = data.get("currencyPair");

        RemoteViews stockViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification_view);

        double difference = Double.parseDouble(currentPrice) - Double.parseDouble(openPrice);
        stockViews.setTextViewText(R.id.price_text, String.format("%s: %s", currencyPair, currentPrice));

        String arrow = "↑";
        if(difference > 0) {
           stockViews.setTextColor(R.id.price_difference_text, getColor(R.color.green));
        }
        else if(difference == 0){
            stockViews.setTextColor(R.id.price_difference_text, getColor(R.color.black));
            arrow = "";
        }
        else{
            stockViews.setTextColor(R.id.price_difference_text, getColor(R.color.red));
            arrow = "↓";
        }

        stockViews.setTextViewText(R.id.price_difference_text, String.format("%.2f %s", difference, arrow));

        int notificationId = 1;
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_show_chart_black_24px)
                .setCustomBigContentView(stockViews)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);

        final NotificationTarget notificationTarget = new NotificationTarget(
                this,
                R.id.chart_img,
                stockViews,
                notification,
                1);

            final Uri uri = Uri.parse(graphUrl);
            Glide.get(getApplicationContext()).clearDiskCache();

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Glide.get(getApplicationContext()).clearMemory();
                    Glide.with( getApplicationContext() )
                            .asBitmap()
                            .load(uri)
                            .into( notificationTarget );
                }
            });
    }
}
