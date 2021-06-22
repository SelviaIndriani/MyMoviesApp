package com.slv.mysubmissionandroidexpert.presentation.setting

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.slv.mysubmissionandroidexpert.R
import com.slv.mysubmissionandroidexpert.presentation.splashscreen.SplashscreenActivity
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {

        const val TYPE_REPEATING = "Repeating Notification"
        const val EXTRA_MESSAGE = "what movie do you want to watch today?"
        const val EXTRA_TYPE = "Daily Reminder"
        private const val ID_REPEATING = 1
    }

    override fun onReceive(context: Context, intent: Intent) {
        val title = EXTRA_TYPE
        val message = intent.getStringExtra(EXTRA_MESSAGE) as String
        val notificationId = ID_REPEATING

        showAlarmNotification(context, title, message, notificationId)
    }

    private fun showAlarmNotification(
        context: Context,
        title: String,
        message: String,
        notificationId: Int
    ) {

        val channelID = "Channel_1"
        val channelName = "Repeating Notification channel"

        val intent = Intent(context, SplashscreenActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            ID_REPEATING,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, channelID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(channelID)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notificationId, notification)

    }

    fun setRepeatingReminder(context: Context, message: String) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,

            pendingIntent
        )
    }

    fun cancelReminder(context: Context, type: String) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)

        val reqCode = if (type.equals(TYPE_REPEATING, ignoreCase = true)) ID_REPEATING else 0

        val pendingIntent = PendingIntent.getBroadcast(context, reqCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
    }
}