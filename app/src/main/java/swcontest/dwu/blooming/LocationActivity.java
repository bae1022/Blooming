package swcontest.dwu.blooming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

import swcontest.dwu.blooming.db.LocationDBManager;
import swcontest.dwu.blooming.dto.LocationDto;
import swcontest.dwu.blooming.service.FetchAddressIntentService;
import swcontest.dwu.blooming.service.LocationService;

public class LocationActivity extends AppCompatActivity {

    public static final String TAG = "LocationActivity";

    private AddressResultReceiver addressResultReceiver;
    double latitude, longitude;

    private GoogleMap mGoogleMap;

    private Marker centerMarker;
    private PolylineOptions pOptions;

    TextView tvAddress;
    LocationDBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        MapsInitializer.initialize(getApplicationContext()); // BitmapDescriptorFactory 생성하기 위한 소스

        addressResultReceiver = new AddressResultReceiver(new Handler());

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapReadyCallBack);

        pOptions = new PolylineOptions();
        pOptions.color(Color.RED);
        pOptions.width(5);

        tvAddress = findViewById(R.id.tvAddress);

        dbManager = new LocationDBManager(this);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            // 동작 실패
            case R.id.btnLocationMemo :
                Intent intent = new Intent(LocationActivity.this, LocationListActivity.class);
                startActivity(intent);
                break;

            case R.id.btnHome :
                Intent hIntent = new Intent(LocationActivity.this, MainActivity.class);
                startActivity(hIntent);
                break;
        }
    }

    OnMapReadyCallback mapReadyCallBack = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mGoogleMap = googleMap;

            LocationService gps = new LocationService(LocationActivity.this);
            if (gps.isGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                LatLng location = new LatLng(latitude, longitude); // 현재 화면에 찍힌 포인트로부터 위도와 경도를 알려줌
                Log.v(TAG, "위도 : " + latitude + ", 경도 : " + longitude);

                long now = System.currentTimeMillis();
                Date date = new Date(now);

                SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy년 M월 dd일");
                SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm");

                String getDay = sdf_day.format(date);
                String getTime = sdf_time.format(date);

                Log.v(TAG, getDay + " " + getTime);

                MarkerOptions options = new MarkerOptions();
                options.position(location);
                options.title("현재 위치");
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                centerMarker = mGoogleMap.addMarker(options);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
                centerMarker.showInfoWindow();

                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(@NonNull Marker marker) {
                        startAddressService();
                    }
                });

                boolean result = dbManager.addLocation(new LocationDto(getDay, getTime, latitude, longitude));
                if (result) {
                    Toast.makeText(LocationActivity.this, "저장 성공!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LocationActivity.this, "저장 실패...", Toast.LENGTH_SHORT).show();
                }
            } else {
                gps.showSettingAlert();
            }
        }
    };

    LocationListener locationListner = new LocationListener() {
        @Override
        // 위치를 받아온 걸로 위치를 이동 시킴 => 안됨... 진짜 너무너무 안됨...
        public void onLocationChanged(Location location) {

            LocationService gps = new LocationService(LocationActivity.this);
            if (gps.isGetLocation()) {
                gps.onLocationChanged(location);
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                LatLng latLng = new LatLng(latitude, longitude); // 현재 화면에 찍힌 포인트로부터 위도와 경도를 알려줌
                Log.v(TAG, "위도 : " + latitude + ", 경도 : " + longitude);

                long now = System.currentTimeMillis();
                Date date = new Date(now);

                SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy년 M월 dd일");
                SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm");

                String getDay = sdf_day.format(date);
                String getTime = sdf_time.format(date);

                Log.v(TAG, getDay + " " + getTime);

                LatLng currentLoc = new LatLng(latitude, longitude);
                Log.v(TAG, "이동 위치 => 위도 : " + latitude + ", 경도 : " + longitude);
                Log.v(TAG, "이동 위치 => 위도 : " + location.getLatitude() + ", 경도 : " + location.getLongitude());
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));
                centerMarker.setPosition(currentLoc);
                pOptions.add(currentLoc);
                mGoogleMap.addPolyline(pOptions);

                boolean result = dbManager.addLocation(new LocationDto(getDay, getTime, latitude, longitude));
                if (result) {
                    Toast.makeText(LocationActivity.this, "저장 성공!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LocationActivity.this, "저장 실패...", Toast.LENGTH_SHORT).show();
                }
            } else {
                gps.showSettingAlert();
            }
        }
    };

    /* 위도/경도 → 주소 변환 IntentService 실행 */
    private void startAddressService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(LocationConstants.RECEIVER, addressResultReceiver);
        intent.putExtra(LocationConstants.LAT_DATA_EXTRA, latitude);
        intent.putExtra(LocationConstants.LNG_DATA_EXTRA, longitude);
        startService(intent);
    }

    /* 위도/경도 → 주소 변환 ResultReceiver */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String addressOutput = null;

            if (resultCode == LocationConstants.SUCCESS_RESULT) {
                if (resultData == null) return;
                addressOutput = resultData.getString(LocationConstants.RESULT_DATA_KEY);
                if (addressOutput == null) addressOutput = "";
                tvAddress.setText(addressOutput);
            } else {
                tvAddress.setText("※ 주소를 찾을 수 없습니다.");
            }
        }
    }
}
