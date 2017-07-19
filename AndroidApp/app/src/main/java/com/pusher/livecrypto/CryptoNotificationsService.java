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

public class CryptoNotificationsService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String graphUrl = remoteMessage.getData().get("graph_url");
        String currentPrice = remoteMessage.getData().get("price");
        String openPrice = remoteMessage.getData().get("open");
        String trend = remoteMessage.getData().get("trend");
        String lowPrice = remoteMessage.getData().get("lowPrice");
        String highPrice = remoteMessage.getData().get("highPrice");

        RemoteViews stockViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.stocks_view);

        String chartEmoji = Integer.parseInt(trend) >= 0 ? "\uD83D\uDCC8" : "\uD83D\uDCC9";
        stockViews.setTextViewText(R.id.price_text, String.format("BTC price - $%s %s", currentPrice, chartEmoji));

        stockViews.setTextColor(R.id.price_text, getTextColour(trend));
        stockViews.setTextViewText(R.id.price_difference_text, String.format("High: $%s | Low: $%s | Open: $%s", highPrice, lowPrice, openPrice));


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

    private int getTextColour(String trend) {
        int textColour;
        switch (trend){
            case "-3":
                textColour = R.color.red3;
                break;

            case "-2":
                textColour = R.color.red2;
                break;

            case "-1":
                textColour = R.color.red1;
                break;

            case "0":
                textColour = R.color.black;
                break;

            case "1":
                textColour = R.color.green1;
                break;

            case "2":
                textColour = R.color.green2;
                break;

            case "3":
                textColour = R.color.green3;
                break;

            default:
                textColour = R.color.black;
                break;
        }
        return getColor(textColour);
    }
}
