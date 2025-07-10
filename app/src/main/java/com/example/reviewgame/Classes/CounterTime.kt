package com.example.reviewgame.Classes

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class CounterTime : Service() {
    private var seconds = 0
    private val handler = Handler(Looper.getMainLooper())
    private var notificationBuilder: NotificationCompat.Builder? = null

    private var minutes = 0

    //Heart Where the time will be counted
    //The logic behind the counter
    // Runnable is somethin that will run and be executed by a thread
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            if(seconds == 59){
                minutes++
                seconds = 1
            }
            //Increment the seconds
            seconds++
            //Give a log in logcat
            Log.d("CounterTime", "Minutes: $minutes" + "Seconds: $seconds")

            //Update the notification text with new time
            if(minutes != 0){
                notificationBuilder!!.setContentText(minutes.toString() + "m " + seconds.toString() + "s")
            }else {
                notificationBuilder!!.setContentText(seconds.toString() + "s")
            }

            //Update the notification
            val manager = NotificationManagerCompat.from(this@CounterTime)

            // This is a safety check. If notification permission is NOT granted, exit the method. Otherwise, update the notification.
            if (ActivityCompat.checkSelfPermission(
                    this@CounterTime,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            manager.notify(NOTIFICATION_ID, notificationBuilder!!.build())

            //Delay execute that after 1 second
            handler.postDelayed(this, 1000)
        }
    }

    //Called by the system when the service is first created. Used for the one time setup ( used once in the current life of the program )
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    //Called by the system every time a client starts the service
    //TO start the service we use Intent
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //Initializes the notification builder
        // We build the notification put the icon, priority and ongoing ( for user dont remove the notification )
        //NotificationCompat is like ContextCompat, is like a bejamin
        notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)


        //Shows the first notification and start the foreground service
        startForeground(NOTIFICATION_ID, notificationBuilder!!.build())

        //Starts the counter
        handler.post(runnable)

        //Tells the system not to recrate the service automatically if its killed for the system for memory
        return START_NOT_STICKY
    }

    //Method to stop the timer
    override fun onDestroy() {
        super.onDestroy()

        //Makes the handler stop any future runnable
        handler.removeCallbacks(runnable)

        Log.d("CounterTime", "Timer stoped") // Log to see that stop
    }

    //Create a notification channel
    //Notification channel is a category of notifications that your app can send
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "Counter Channel", NotificationManager.IMPORTANCE_LOW
            )
            // Calls the system to create the notification channel
            getSystemService(NotificationManager::class.java).createNotificationChannel(
                serviceChannel
            )
        }
    }


    //onBind is necessary for the service, but we arent use that so
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        // Notification Constants
        const val CHANNEL_ID: String = "CounterTimeChannel"
        const val NOTIFICATION_ID: Int = 1
    }
} //THIS CODE CREATE THE LOGIC BEHIND THE COUNTER


