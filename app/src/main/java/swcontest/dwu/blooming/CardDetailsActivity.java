package swcontest.dwu.blooming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import swcontest.dwu.blooming.db.CardDBManager;
import swcontest.dwu.blooming.dto.CardDto;

public class CardDetailsActivity extends AppCompatActivity {

    CardListAdapter adapter;
    ArrayList<CardDto> list;
    CardDBManager dbManager;

    ListView lvList;
    TextView tvTitle, tvPrice, tvDate, tvChange;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        intent = getIntent();

        lvList = findViewById(R.id.CardList);
        tvTitle = findViewById(R.id.tvTitle);
        tvPrice = findViewById(R.id.tvPrice);
        tvDate = findViewById(R.id.tvDate);
        tvChange = findViewById(R.id.tvChange);

        list = new ArrayList();

        adapter = new CardListAdapter(this, R.layout.custom_card, list);

        lvList.setAdapter(adapter);

        dbManager = new CardDBManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        list.addAll(dbManager.getAllCard());
        adapter.notifyDataSetChanged();
    }

}
