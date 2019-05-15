package com.bjit.shipon.superlocationpro.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.bjit.shipon.superlocationpro.activity.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;

public class LocationTracker extends Service {


    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private double wayLatitude, wayLongitude;
    private Notification.Builder builder;
    private Notification notification;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        Log.d("ppp",""+location.toString());

                        StringBuilder stringBuilder =new StringBuilder();
                        stringBuilder.append(wayLatitude);
                        stringBuilder.append("-");
                        stringBuilder.append(wayLongitude);
                        stringBuilder.append("\n\n");
                        Log.d("ppp",stringBuilder.toString());


                    }
                }
            }
        };

        try {
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }catch (SecurityException e){
            Log.d("ppp","faild");

        }



        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(LocationTracker.this, MainActivity.class), 0);

        /*Handle Android O Notifs as they need channel when targeting 28th SDK*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel notificationChannel = new NotificationChannel(
                    "download_check_channel_id",
                    "Channel name",
                    NotificationManager.IMPORTANCE_LOW);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }

            builder = new Notification.Builder(this.getBaseContext(), notificationChannel.getId())
                    .setContentTitle("Hi! I'm service")
                    .setContentIntent(pendingIntent)
                    .setOngoing(true);

            notification = builder.build();
            startForeground("StackOverflow".length(), notification);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mFusedLocationClient!=null) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
