package swcontest.dwu.blooming.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;

import swcontest.dwu.blooming.db.LocationDBManager;
import swcontest.dwu.blooming.dto.LocationDto;

import static swcontest.dwu.blooming.userSetting.StartActivity.location_period;

public class LocationService extends Service {

    public static final String TAG = "LocationService";

    private FusedLocationProviderClient mFusedLocationClient;
    int period;
    private static long UPDATE_INTERNAL = 1000 * 60;
    private static long FASTEST_UPDATE_INTERNAL = 1000 * 60;
    private LocationDBManager dbManager;


    public LocationService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "LocationService service created");

        period = location_period;
        dbManager = new LocationDBManager(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_location_channel";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "My Location Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand : called.");
        getLocation();
        return START_STICKY;
    }

    private void getLocation() {
        UPDATE_INTERNAL = 1000 * 60 * period;
        FASTEST_UPDATE_INTERNAL = 1000 * 60 * period;

        LocationRequest mLocationRequestHighAccuracy = new LocationRequest();
        mLocationRequestHighAccuracy.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequestHighAccuracy.setInterval(UPDATE_INTERNAL);
        mLocationRequestHighAccuracy.setFastestInterval(FASTEST_UPDATE_INTERNAL);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "getLocation : stopping the location service.");
            stopSelf();
            return;
        }
        Log.d(TAG, "getLocation : getting location information.");
        mFusedLocationClient.requestLocationUpdates(mLocationRequestHighAccuracy, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.d(TAG, "onLocationResult : got location result.");

                Location location = locationResult.getLastLocation();

                if (location != null) {
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);

                    SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy년 M월 dd일");
                    SimpleDateFormat sdf_time = new SimpleDateFormat("kk:mm:ss");

                    String getDay = sdf_day.format(date);
                    String getTime = sdf_time.format(date);

                    Log.d(TAG, getDay + " " + getTime);

                    boolean result = dbManager.addLocation(new LocationDto(getDay, getTime, location.getLatitude(), location.getLongitude()));
                    if (result) {
                        Log.d(TAG, "저장 성공!!!");
                    } else {
                        Log.d(TAG, "저장 실패...");
                    }
                }
            }
        }, Looper.myLooper());
    }



}