package swcontest.dwu.blooming;

import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class diaryActivity extends AppCompatActivity {

    protected  void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        MaterialCalendarView materialCalendarView = findViewById(R.id.calendarView);
        materialCalendarView.setSelectedDate(CalendarDay.today());
        materialCalendarView.addDecorator(new EventDecorator(diaryActivity.this));
    }

}
