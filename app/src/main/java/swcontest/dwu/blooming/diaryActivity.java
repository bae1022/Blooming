package swcontest.dwu.blooming;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import swcontest.dwu.blooming.diary.EventDecorator;
import swcontest.dwu.blooming.diary.SaturdayDecorator;
import swcontest.dwu.blooming.diary.SundayDecorator;
import swcontest.dwu.blooming.diary.OneDayDecorator;

public class diaryActivity extends AppCompatActivity {

    MaterialCalendarView materialCalendarView;
    public static final int sub = 1001;

    protected  void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        materialCalendarView = findViewById(R.id.calendarView);
        //오늘날짜지정(파란색 원으로)
        //materialCalendarView.setSelectedDate(CalendarDay.today());
        //OneDayDecorator-오늘날짜, 토요일, 일요일 색변환.
        materialCalendarView.addDecorators( new SundayDecorator(),
                new SaturdayDecorator(), new OneDayDecorator());

        //점표시할 날짜 result
        String[] result = {"2021,08,18","2021,08,19","2021,08,20","2021,08,31"};

        new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();

                Log.i("Year test", Year + "");
                Log.i("Month test", Month + "");
                Log.i("Day test", Day + "");

                String shot_Day = Year + "," + Month + "," + Day;

                Log.i("shot_Day test", shot_Day + "");
                materialCalendarView.clearSelection();

                Toast.makeText(getApplicationContext(), shot_Day , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(diaryActivity.this, KeepDiaryActivity.class);
                intent.putExtra("year", Year);
                intent.putExtra("month", Month);
                intent.putExtra("day", Day);
                startActivityForResult(intent,sub);//액티비티 띄우기

            }
        });

    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(String[] Time_Result) {
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();


            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로 짜르고 string을 int 로 변환
            for (int i = 0; i < Time_Result.length; i++) {


                //이부분에서 day를 선언하면 초기 값에 오늘 날짜 데이터 들어간다.
                //오늘 날짜 데이터를 첫 번째 인자로 넣기 때문에 데이터가 하나씩 밀려 마지막 데이터는 표시되지 않고, 오늘 날짜 데이터가 표시 됨.
                // day선언 주석처리

                //                CalendarDay day = CalendarDay.from(calendar);
                //                Log.e("데이터 확인","day"+day);
                String[] time = Time_Result[i].split(",");

                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                //선언문을 아래와 같은 위치에 선언
                //먼저 .set 으로 데이터를 설정한 다음 CalendarDay day = CalendarDay.from(calendar); 선언해주면 첫 번째 인자로 새로 정렬한 데이터를 넣어 줌.
                calendar.set(year, month - 1, dayy);
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);

            }
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            materialCalendarView.addDecorator(new EventDecorator(Color.LTGRAY, calendarDays,diaryActivity.this));
        }
    }
}
