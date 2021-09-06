package swcontest.dwu.blooming;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
        
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(LocationListActivity.this);
                builder.setTitle("위치 기록 삭제")
                        .setIcon(R.drawable.ic_location)
                        .setMessage("정말로 " + list.get(pos).getDate() + " 의 위치 기록을 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dbManager.removeLocation(list.get(pos).getDate())) {
                                    Toast.makeText(getApplicationContext(), list.get(pos).getDate() + "의 기록이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    list.clear();
                                    list.addAll(dbManager.getLocation());
                                    adapter.notifyDataSetChanged();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), list.get(pos).getDate() + "의 기록이 삭제가 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setCancelable(false)
                        .show();

                return true;
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCurrentLocation :
                Intent intent = new Intent(this, LocationActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnMainGo :
                Intent hintent = new Intent(this, MainActivity.class);
                startActivity(hintent);
                finish();
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