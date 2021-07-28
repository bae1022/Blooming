package swcontest.dwu.blooming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        }
        if (intent != null)
            startActivity(intent);

    }
}