package swcontest.dwu.blooming;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
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

// 여기까지 넘어오지 못함..
public class LocationDetailActivity extends AppCompatActivity {

    public static final String TAG = "LocationMemoDetailActivity";

    double latitude, longitude;

    private GoogleMap mGoogleMap;
    private LocationManager locationManager;

    private Marker centerMarker;
    private PolylineOptions pOptions;

    TextView tv_memo;
    LocationDBManager dbManager;
    LocationDto dto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_memo_detail);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_memo);
//        mapFragment.getMapAsync(mapReadyCallBack);

        pOptions = new PolylineOptions();
        pOptions.color(Color.BLUE);
        pOptions.width(5);

        tv_memo = findViewById(R.id.tv_memo_Location);

        dbManager = new LocationDBManager(this);
        dto = (LocationDto) getIntent().getSerializableExtra("location");

        tv_memo.setText(dto.getDate() + "의 기록");
    }

//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnLocationMemo :
//                Intent intent = new Intent(this, LocationMemoListActivity.class);
//                break;
//        }
//    }

//    OnMapReadyCallback mapReadyCallBack = new OnMapReadyCallback() {
//        @Override
//        public void onMapReady(GoogleMap googleMap) {
//            mGoogleMap = googleMap;
//
//            LatLng location = new LatLng(latitude, longitude);
//
//            MarkerOptions options = new MarkerOptions();
//            options.position(location);
//            options.title("기록");
//            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//
//            centerMarker = mGoogleMap.addMarker(options);
//            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
//            centerMarker.showInfoWindow();
//            pOptions.add(location);
//            mGoogleMap.addPolyline(pOptions);
//        }
//    };
}
