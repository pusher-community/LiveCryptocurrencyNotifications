package com.pusher.livecrypto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pusher.android.PusherAndroid;
import com.pusher.android.notifications.ManifestValidator;

public class MainActivity extends AppCompatActivity {

    PusherAndroid pusher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pusher = new PusherAndroid("YOUR_KEY_HERE");
        try {
            pusher.nativePusher().registerFCM(this);
            pusher.nativePusher().subscribe("bitcoin");

        } catch (ManifestValidator.InvalidManifestException e) {
            e.printStackTrace();
        }
    }
}
