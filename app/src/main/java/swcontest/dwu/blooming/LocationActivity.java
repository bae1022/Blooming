package swcontest.dwu.blooming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.Date;

import swcontest.dwu.blooming.db.LocationDBManager;
import swcontest.dwu.blooming.dto.LocationDto;
import swcontest.dwu.blooming.service.FetchAddressIntentService;

import static swcontest.dwu.blooming.MainActivity.period;

public class LocationActivity extends AppCompatActivity {

    public static final String TAG = "LocationActivity";

    private static Handler mHandler;
    private AddressResultReceiver addressResultReceiver;
    double latitude, longitude;

    private MapFragment mapFragment;
    private GoogleMap mGoogleMap;
    private Marker centerMarker;
    private PolylineOptions pOptions;
    private ArrayList<LatLng> arrayPoints = null;

    TextView tvAddress, tvGuide2;
    LocationDBManager dbManager;
    ArrayList<LocationDto> list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        addressResultReceiver = new AddressResultReceiver(new Handler());
        tvAddress = findViewById(R.id.tvAddress);

        tvGuide2 = findViewById(R.id.tvGuide2);
        tvGuide2.setText("(현재 위치와 경로는 약 " + period + "분마다 갱신됩니다.)");

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapReadyCallBack);

        pOptions = new PolylineOptions();
        pOptions.color(Color.RED);
        pOptions.width(5);

        dbManager = new LocationDBManager(this);
        list = new ArrayList<LocationDto>();
        arrayPoints = new ArrayList<LatLng>();

        updateMap();
    }

    public void onClick(View v) {
        switch (v.getId()) {
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

            long now = System.currentTimeMillis();
            Date date = new Date(now);

            SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy년 M월 dd일");
            String getDay = sdf_day.format(date);

            list.clear();
            list.addAll(dbManager.getLocationByDate(getDay));

            if (list.size() != 0) {
                latitude = list.get(list.size() - 1).getLatitude();
                longitude = list.get(list.size() - 1).getLongitude();

                LatLng location = new LatLng(latitude, longitude);
                Log.v(TAG, "위도 : " + latitude + ", 경도 : " + longitude);

                MarkerOptions options = new MarkerOptions();
                options.position(location);
                options.title("현재 위치");
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                centerMarker = mGoogleMap.addMarker(options);
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
                centerMarker.showInfoWindow();

                for (int i = 0; i < list.size(); i++) {
                    LatLng latLng = new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude());
                    arrayPoints.add(latLng);
                }
                pOptions.addAll(arrayPoints);
                mGoogleMap.addPolyline(pOptions);
            }

            mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(@NonNull Marker marker) {
                    startAddressService();
                }
            });
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

    public void updateMap() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                mGoogleMap.clear();
                Log.d("Location", "mapReadyCallBack");
                mapFragment.getMapAsync(mapReadyCallBack);
            }
        };

        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000 * 60 * 3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(0);
                }
            }
        }

        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();
    }
}
