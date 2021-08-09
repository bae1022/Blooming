package swcontest.dwu.blooming;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import swcontest.dwu.blooming.diary.EventDecorator;
import swcontest.dwu.blooming.diary.SaturdayDecorator;
import swcontest.dwu.blooming.diary.SundayDecorator;
import swcontest.dwu.blooming.diary.OneDayDecorator;

public class diaryActivity extends AppCompatActivity {


    protected  void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        MaterialCalendarView materialCalendarView = findViewById(R.id.calendarView);
        //오늘날짜지정(파란색 원으로)
        //materialCalendarView.setSelectedDate(CalendarDay.today());
        //OneDayDecorator-오늘날짜지정, 토요일, 일요일 색변환.
        materialCalendarView.addDecorators( new SundayDecorator(),
                new SaturdayDecorator(), new OneDayDecorator());
    }

}
