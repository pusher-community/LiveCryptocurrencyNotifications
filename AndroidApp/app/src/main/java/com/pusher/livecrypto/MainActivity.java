package com.pusher.livecrypto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.pusher.android.PusherAndroid;
import com.pusher.android.notifications.ManifestValidator;
import com.pusher.android.notifications.PushNotificationRegistration;
import com.pusher.android.notifications.fcm.FCMPushNotificationReceivedListener;
import com.pusher.android.notifications.tokens.PushNotificationRegistrationListener;

public class MainActivity extends AppCompatActivity {

    PusherAndroid pusher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        pusher = new PusherAndroid("a446158132cfaa3ed747");
//        initPushNotifications(pusher);
//        try {
//            pusher.nativePusher().registerFCM(this);
//            pusher.nativePusher().subscribe("bitcoin");
//
//        } catch (ManifestValidator.InvalidManifestException e) {
//            e.printStackTrace();
//        }
        Log.d(">>>>>>>>>", ">>>>>>>>> onCreate");
        initPushNotifications();
    }

    public void initPushNotifications(){
            pusher = new PusherAndroid("a446158132cfaa3ed747");
            PushNotificationRegistration nativePusher = pusher.nativePusher();
        try {
            nativePusher.registerFCM(this);
            nativePusher.subscribe("BTC_USD");

        } catch (ManifestValidator.InvalidManifestException e) {
            e.printStackTrace();
        }


//        final PushNotificationRegistration nativePusher = pusher.nativePusher();
//            nativePusher.setFCMListener(new FCMPushNotificationReceivedListener() {
//                @Override
//                public void onMessageReceived(RemoteMessage remoteMessage) {
//                    Log.d(">>>>>>>>>", ">>>>>>> PUSH RECEIVED");
//                    Log.d(">>>>>>>>>", remoteMessage.getData().toString());
//                }
//            });
//            try {
//                nativePusher.registerFCM(this, new PushNotificationRegistrationListener() {
//                    @Override
//                    public void onSuccessfulRegistration() {
//                        Log.d(">>>>>>>>>", "successful registration");
//                        String interest = "bitcoin";
//                        nativePusher.subscribe(interest);
//                    }
//
//                    @Override
//                    public void onFailedRegistration(int statusCode, String response) {
//                        Log.d(">>>>>>>>>", "failure");
//                    }
//                });
//
//            } catch (ManifestValidator.InvalidManifestException e) {
//                e.printStackTrace();
//            }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(">>>>>>>>>", ">>>>>>>>> onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(">>>>>>>>>", ">>>>>>>>> onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(">>>>>>>>>", ">>>>>>>>> onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(">>>>>>>>>", ">>>>>>>>> onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(">>>>>>>>>", ">>>>>>>>> onDestroy");

    }
}
