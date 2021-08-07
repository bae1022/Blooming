package swcontest.dwu.blooming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyMemoActivity extends AppCompatActivity {

    Button memo_ok;
    Button memo_cancel;

    EditText memo_content;

    TextView memo_day;
    TextView memo_time;
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

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.daily_memo_ok:  //데이터베이스에 메모 삽입

                break;

            case R.id.daily_memo_cancel: //메인화면으로 이동
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}