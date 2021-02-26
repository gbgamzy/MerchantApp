@file:Suppress("DEPRECATION")

package com.example.merchantapp.classes

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId

import com.google.firebase.iid.FirebaseInstanceIdService


class MyFirebaseInstanceIdService : FirebaseInstanceIdService() {
    //this method will be called
    //when the token is generated
    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d("TAG", "Refreshed token: $refreshedToken")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

    }
}