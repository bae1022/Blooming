package swcontest.dwu.blooming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import swcontest.dwu.blooming.db.DailyMemoDBHelper;

public class DailyMemoActivity extends AppCompatActivity {

    EditText memo_content;

    TextView memo_day;
    TextView memo_time;

    DailyMemoDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_memo);

        memo_content = findViewById(R.id.memo_content);
        memo_day = findViewById(R.id.memo_day);
        memo_time = findViewById(R.id.memo_time);

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy년 M월 dd일");
        SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm");

        String getDay = sdf_day.format(date);
        String getTime = sdf_time.format(date);

        memo_day.setText(getDay);
        memo_time.setText(getTime);

        helper = new DailyMemoDBHelper(this);
    }

    public void onClick(View v) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Intent intent = new Intent(this, MainActivity.class);

        switch (v.getId()) {
            case R.id.daily_memo_ok:  //데이터베이스에 메모 삽입

                ContentValues row = new ContentValues();

                row.put(helper.COL_DATE, memo_day.getText().toString());
                row.put(helper.COL_TIME, memo_time.getText().toString());
                row.put(helper.COL_CONTENT, memo_content.getText().toString());

                db.insert(helper.TABLE_NAME, null, row);

                helper.close();
                Toast.makeText(this, memo_time.getText().toString() + ", 일상 기록이 추가되었습니다.", Toast.LENGTH_SHORT);


                startActivity(intent);

                break;

            case R.id.daily_memo_cancel: //메인화면으로 이동

                Intent intent2 = new Intent(this, DailyMemoListActivity.class);
                startActivity(intent2);
                break;
        }
    }
}