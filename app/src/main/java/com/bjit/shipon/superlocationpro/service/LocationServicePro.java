package com.bjit.shipon.superlocationpro.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.bjit.shipon.superlocationpro.R;
import com.bjit.shipon.superlocationpro.activity.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsResult;


public class LocationServicePro extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    // google api client
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    PendingResult<LocationSettingsResult> result;
    final static int REQUEST_LOCATION = 199;





    private static final String TAG = "ppp";

    //private  final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for Network status
    boolean isNetworkEnabled = false;

    // flag for location status
    boolean canGetLocation;

    Location location; // location
    double latitude;   // latitude
    double longitude;  // longitude


    // Declaring a location Manager
    protected LocationManager locationManager;

    public static final String ACTION_LOCATION_BROADCAST = LocationServicePro.class.getName() + "LocationBroadcast";
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";
    private Notification.Builder builder;
    private Notification notification;


    public LocationServicePro(){
        super();
    }



//    public LocationServicePro(Context context){
//        this.mContext = context;
//
//
////        // Connect to location service via google api client
////        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
////                .addApi(LocationServices.API)
////                .addConnectionCallbacks(this)
////                .addOnConnectionFailedListener(this).build();
////        mGoogleApiClient.connect();
//
//
//        // update location
//        getLocation();
//    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

       getLocation();
        Log.d("popop","start"+latitude);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(LocationServicePro.this, MainActivity.class), 0);

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
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);

        //getLocation();

        // Turn on Gps if off
        if(!isGPSEnabled && !isNetworkEnabled){
            // gps is off turn it on
            //showSettingAlert();
        }

    }








    /**
     * For getting the current location of the
     * device
     * @return location the location of device
     *                  based on GPS or NetworkProvider
     */
    public Location getLocation() {
        try{
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);

            String provider = locationManager.getBestProvider(criteria, true);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,0, this);

            //getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            //getting Network Status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled){
                //showSettingAlert();
            } else {
                this.canGetLocation = true;

                // First get location from Network Provider
                if(isNetworkEnabled){
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            1000,
                            0, this
                    );

                    Log.d(TAG, "Network");
                    if(locationManager != null){
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                if(isGPSEnabled){
                    if (location ==null){
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                1000,
                                0, this
                        );

                        Log.d(TAG, "GPS");
                        if(locationManager != null){
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }

                        }


                    }
                }
            }

        }catch (SecurityException e){

        }
        return location;
    }




    public void showSettingAlert(){
        canGetLocation = true;


//        /**
//         * Use this for
//         * Turn GPS on Without going to device setting
//         */
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(mLocationRequest);
//        builder.setAlwaysShow(true);
//
//        result = SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
//
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            @Override
//            public void onResult(LocationSettingsResult result) {
//                final Status status = result.getStatus();
//                //final LocationSettingsStates state = result.getLocationSettingsStates();
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        // All location settings are satisfied. The client can initialize location
//                        // requests here.
//                        //...
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        // Location settings are not satisfied. But could be fixed by showing the user
//                        // a dialog.
//                        try {
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//                            status.startResolutionForResult(
//                                    (Activity) mContext,
//                                    REQUEST_LOCATION);
//                            canGetLocation = true;
//                        } catch (IntentSender.SendIntentException e) {
//                            // Ignore the error.
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        // Location settings are not satisfied. However, we have no way to fix the
//                        // settings so we won't show the dialog.
//                        //...
//                        break;
//                }
//            }
//        });



        /**
         * Use this for
         * Turn GPS on BY going to device setting
         */


        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);

        //Setting Dialog Title
        alBuilder.setTitle("GPS Setting");

        // Dialog message
        alBuilder.setMessage("Gps is not enabled. Do you want to enable it now?");

        // On pressing setting button
        alBuilder.setPositiveButton("setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getApplication().startActivity(intent);
            }
        });

        // on pressing cancel button
        alBuilder.setNegativeButton("canel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alBuilder.show();
    }


    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(LocationServicePro.this);
        }
    }



    /**
     * Function to get latitude
     * */

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */

    public boolean canGetLocation() {
        return this.canGetLocation;
    }






    @Override
    public void onLocationChanged(Location location) {
        this.location = location ;
        Log.d("ppp","changed"+latitude);




        if (location != null) {
            Log.d("ppp", "== location != null");

            //Send result to activities
            //sendMessageToUI(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        }

    }
    private void sendMessageToUI(String lat, String lng) {

        Log.d(TAG, "Sending info...");

        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(EXTRA_LATITUDE, lat);
        intent.putExtra(EXTRA_LONGITUDE, lng);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        getLocation();

    }

    @Override
    public void onProviderEnabled(String provider) {
        getLocation();

    }

    @Override
    public void onProviderDisabled(String provider) {

        //showSettingAlert();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ppp","onbind"+latitude);
        getLocation();
        return null;
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
