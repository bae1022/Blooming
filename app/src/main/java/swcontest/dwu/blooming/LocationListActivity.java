package swcontest.dwu.blooming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import swcontest.dwu.blooming.adapter.LocationAdapter;
import swcontest.dwu.blooming.db.LocationDBManager;
import swcontest.dwu.blooming.dto.LocationDto;

public class LocationListActivity extends AppCompatActivity {

    ListView listView;
    LocationAdapter adapter;
    ArrayList<LocationDto> list = null;
    LocationDBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_memo_list);

        listView = (ListView) findViewById(R.id.lvLocation);
        list = new ArrayList();
        adapter = new LocationAdapter(this, R.layout.activity_location_memo, list);
        listView.setAdapter(adapter);
        dbManager = new LocationDBManager(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LocationDto location = list.get(position);
                Intent intent = new Intent(LocationListActivity.this, LocationDetailActivity.class);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCurrentLocation :
                Intent intent = new Intent(this, LocationActivity.class);
                startActivity(intent);
                break;
            case R.id.btnMainGo :
                Intent hintent = new Intent(this, MainActivity.class);
                startActivity(hintent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        list.addAll(dbManager.getLocation());
        adapter.notifyDataSetChanged();
    }

}