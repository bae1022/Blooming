package swcontest.dwu.blooming.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
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

import swcontest.dwu.blooming.MainActivity;
import swcontest.dwu.blooming.R;
import swcontest.dwu.blooming.db.LocationDBManager;
import swcontest.dwu.blooming.dto.LocationDto;

import static swcontest.dwu.blooming.MainActivity.period;

public class LocationService extends Service {

    public static final String TAG = "LocationService";

    private FusedLocationProviderClient mFusedLocationClient;
    private static long UPDATE_INTERNAL = 1000 * 60;
    private static long FASTEST_UPDATE_INTERNAL = 1000 * 60;
    private LocationDBManager dbManager;
    private LocationCallback locationCallback;
    NotificationManager Notifi_M;
    LocationServiceThread thread;

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

        dbManager = new LocationDBManager(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand : called.");

        Notifi_M = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        serviceHandler handler = new serviceHandler();
        thread = new LocationServiceThread(handler);
        thread.start();

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
        mFusedLocationClient.requestLocationUpdates(mLocationRequestHighAccuracy,
                locationCallback, Looper.myLooper());
    }

    private void locationCallback() {
        locationCallback = new LocationCallback() {
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
                        Log.d(TAG, "현재 위치의 위도 : " + location.getLatitude() + ", 경도 : " + location.getLongitude());
                        Log.d(TAG, "저장 성공!!!");
                    } else {
                        Log.d(TAG, "저장 실패...");
                    }
                }
            }
        };
    }

    // 포그라운드 서비스
    public void initializeNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");
        builder.setSmallIcon(R.drawable.ic_location);
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText("설정을 보려면 누르세요.");
        style.setBigContentTitle(null);
        style.setSummaryText("GPS(위치) 서비스 동작 중");
        builder.setContentText(null);
        builder.setContentTitle(null);
        builder.setOngoing(true);
        builder.setStyle(style);
        builder.setWhen(0);
        builder.setShowWhen(false);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("1", "포그라운드 서비스", NotificationManager.IMPORTANCE_NONE));
        }
        Notification notification = builder.build();
        startForeground(1, notification);
    }

    class serviceHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            initializeNotification();
            getLocation();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFusedLocationClient.removeLocationUpdates(locationCallback);
        thread.stopForever();
        thread = null; // 쓰레기 값 만들어서 빠르게 회수하라고 null 값을 넣어줌.
        stopForeground(true);
        Log.d(TAG, "LocationService : onDestroy() 실행");
    }
}