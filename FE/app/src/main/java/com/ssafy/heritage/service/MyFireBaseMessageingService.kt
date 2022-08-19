package com.ssafy.heritage.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.data.dto.FCMToken
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.util.Channel.CHANNEL_ID
import com.ssafy.heritage.util.JWTUtils
import com.ssafy.heritage.view.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MyFireBaseMessageingService___"

class MyFireBaseMessageingService : FirebaseMessagingService() {

    private val repository = Repository.get()

    // 새로운 토큰이 생성될 때 마다 해당 콜백이 호출된다.
    @SuppressLint("LongLogTag")
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        val jwtToken = ApplicationClass.sharedPreferencesUtil.getToken()

        // 유저가 로그인 되어있는 경우에만 FCM토큰 서버로 전송
        jwtToken?.let {

            val user = JWTUtils.decoded(jwtToken)

            CoroutineScope(Dispatchers.Main).launch {

                val fcmToken = FCMToken(user?.userSeq!!, token)
                repository.pushToken(fcmToken)
            }

            // 새로운 토큰 로그 남기기
            Log.d(TAG, "onNewFCMToken: $token")
        }
    }

    // Foreground에서 Push Service를 받기위해 Notification 설정
    @SuppressLint("LongLogTag")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "onMessageReceived: ")

        remoteMessage.notification.let { message ->
            val messageTitle = message!!.title
            val messageContent = message!!.body

            val mainIntent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val mainPendingIntent =
                PendingIntent.getActivity(this, 0, mainIntent, FLAG_MUTABLE)

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_w)
                .setContentTitle(messageTitle)
                .setContentText(messageContent)
                .setAutoCancel(true)
                .setContentIntent(mainPendingIntent)

            NotificationManagerCompat.from(this).apply {
                notify(101, builder.build())
            }
        }
    }
}