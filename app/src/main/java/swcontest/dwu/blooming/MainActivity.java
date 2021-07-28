package swcontest.dwu.blooming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

        }
        if (intent != null)
            startActivity(intent);


        Button diaryButton = findViewById(R.id.btn_diary);
        diaryButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),diaryActivity.class);
                startActivity(intent);
            }
        });
    }
}