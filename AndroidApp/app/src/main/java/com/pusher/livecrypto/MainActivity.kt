package com.pusher.livecrypto

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.firebase.messaging.RemoteMessage
import com.pusher.android.PusherAndroid
import com.pusher.android.notifications.ManifestValidator
import com.pusher.android.notifications.PushNotificationRegistration
import com.pusher.android.notifications.fcm.FCMPushNotificationReceivedListener
import com.pusher.android.notifications.interests.InterestSubscriptionChangeListener
import com.pusher.android.notifications.tokens.PushNotificationRegistrationListener

class MainActivity : AppCompatActivity() {

    internal lateinit var pusher: PusherAndroid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pusher = PusherAndroid("a446158132cfaa3ed747")
        val nativePusher = pusher.nativePusher()
        try {
            nativePusher.registerFCM(this)
            nativePusher.subscribe("BTC_USD")

        } catch (e: ManifestValidator.InvalidManifestException) {
            e.printStackTrace()
        }

    }
}
