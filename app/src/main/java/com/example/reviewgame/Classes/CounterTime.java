package com.example.reviewgame.Classes;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CounterTime extends Service {

    // Notification Constants
    public static final String CHANNEL_ID = "CounterTimeChannel";
    public static final int NOTIFICATION_ID = 1;

    private int seconds = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private NotificationCompat.Builder notificationBuilder;

    //Heart Where the time will be counted
    //The logic behind the counter
    private final  Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //Increment the seconds
            seconds++;
            //Give a log
            Log.d("CounterTime", "Seconds: " + seconds);

            //Update the notification text with new time
            notificationBuilder.setContentText(seconds + "s");

            //Update the notification
            NotificationManagerCompat manager = NotificationManagerCompat.from(CounterTime.this);

            // This is a safety check. If notification permission is NOT granted, exit the method. Otherwise, update the notification.
            if (ActivityCompat.checkSelfPermission(CounterTime.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            manager.notify(NOTIFICATION_ID, notificationBuilder.build());

            //Delay execute that after 1 second
            handler.postDelayed(this, 1000);
        }
    };

    //Called by the system when the service is first created. Used for the one time setup ( used once in the current life of the program )
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    //Called by the system every time a client starts the service
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Initializes the notification builder
        notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true); // Impede que o usuário remova a notificação arrastando



        //Shows the first notification and start the foreground service
        startForeground(NOTIFICATION_ID, notificationBuilder.build());

        //Starts the counter
        handler.post(runnable);

        //Tells the system not to recrate the service automatically if its killed for the system for memory
        return START_NOT_STICKY;
    }

    //Method to stop the timer
    @Override
    public void onDestroy() {
        super.onDestroy();

        //Makes the handler stop any future runnable
        handler.removeCallbacks(runnable);

        Log.d("CounterTime", "Timer parado e serviço destruído."); // Um log para confirmar que parou
    }

    //Create a notification channel
    //Notification channel is a category of notifications that your app can send
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID, "Canal do Contador", NotificationManager.IMPORTANCE_LOW
            );
            //CHAMA O GERENCIADOR DE NOTIFICAÇÕES DO SISTEMA ||| CRIA O CANAL DE FATO
            getSystemService(NotificationManager.class).createNotificationChannel(serviceChannel);
        }
    }


    //onBind is necessary for the service, but we arent use that so
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


//THIS CODE CREATE THE LOGIC BEHIND THE COUNTER

