package swcontest.dwu.blooming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import swcontest.dwu.blooming.service.DailyMemoService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //일상 기록 Notification 설정_개발하실 때 주석처리 해주세요
        Toast.makeText(getApplicationContext(),"Service 시작", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, DailyMemoService.class);
        startService(intent);

        //알람 종료
//        Intent intent = new Intent(MainActivity.this,DailyMemoService.class);
//        stopService(intent);
    }

    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btn_life: //일상 기록
                intent = new Intent(this, DailyMemoActivity.class);
                break;

            case R.id.btn_diary: //일기
                intent = new Intent(this,diaryActivity.class);
                break;

            case R.id.btn_card: //카드 내역
                intent = new Intent(this, CardDetailsActivity.class);
                break;

            case R.id.btn_check:

                break;
        }
        if (intent != null)
            startActivity(intent);

    }
}