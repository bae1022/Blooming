package swcontest.dwu.blooming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import swcontest.dwu.blooming.diary.CustomCalendarView;

public class DiaryActivity extends AppCompatActivity {

    CustomCalendarView customCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        customCalendarView = (CustomCalendarView)findViewById(R.id.custom_calendar_view);

    }
}