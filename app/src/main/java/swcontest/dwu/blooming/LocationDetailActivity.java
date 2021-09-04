package swcontest.dwu.blooming;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import swcontest.dwu.blooming.db.LocationDBManager;
import swcontest.dwu.blooming.dto.LocationDto;

public class LocationDetailActivity extends AppCompatActivity {

    public static final String TAG = "LocationDetailActivity";

    private GoogleMap mGoogleMap;
    private Marker startMarker, endMarker;
    private PolylineOptions pOptions;
    private ArrayList<LatLng> arrayPoints = null;

    LocationDBManager dbManager;
    ArrayList<LocationDto> list = null;

    TextView tv_memo;
    LocationDto dto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_memo_detail);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_memo);
        mapFragment.getMapAsync(mapReadyCallBack);

        pOptions = new PolylineOptions();
        pOptions.color(Color.BLUE);
        pOptions.width(5);

        tv_memo = findViewById(R.id.tv_memo_Location);

        dbManager = new LocationDBManager(this);
        dto = (LocationDto) getIntent().getSerializableExtra("location");
        list = new ArrayList<LocationDto>();
        arrayPoints = new ArrayList<LatLng>();

        tv_memo.setText(dto.getDate() + "의 기록");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_memo_home :
                Intent intent = new Intent(LocationDetailActivity.this, LocationListActivity.class);
                startActivity(intent);
                break;
        }
    }

    OnMapReadyCallback mapReadyCallBack = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mGoogleMap = googleMap;

            list.clear();
            list.addAll(dbManager.getLocationByDate(dto.getDate()));

            if (list.size() != 0) {
                LatLng startLocation = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());
                LatLng endLocation = new LatLng(list.get(list.size() - 1).getLatitude(), list.get(list.size() - 1).getLongitude());

                MarkerOptions options_start = new MarkerOptions();
                options_start.position(startLocation);
                options_start.title("시작 지점");
                options_start.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                startMarker = mGoogleMap.addMarker(options_start);
                startMarker.showInfoWindow();

                MarkerOptions options_end = new MarkerOptions();
                options_end.position(endLocation);
                options_end.title("마자막 지점");
                options_end.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                endMarker = mGoogleMap.addMarker(options_end);
                endMarker.showInfoWindow();

                for (int i = 0; i < list.size(); i++) {
                    LatLng latLng = new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude());
                    arrayPoints.add(latLng);
                }

                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(endLocation, 14));
                pOptions.addAll(arrayPoints);
                mGoogleMap.addPolyline(pOptions);
            }
        }
    };
}
