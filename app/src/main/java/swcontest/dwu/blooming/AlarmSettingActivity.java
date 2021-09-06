package swcontest.dwu.blooming;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import swcontest.dwu.blooming.db.UserDBHelper;
import swcontest.dwu.blooming.diary.AlertReceiver;
import swcontest.dwu.blooming.diary.TimePickerFragment;

public class AlarmSettingActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    public  static  final String TAG = "Alarm_Setting_Activity";
    private TextView time_text;

    public static String minute_sleep;
    public static String hour_sleep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

        time_text = findViewById(R.id.time_text);
        Button time_btn = findViewById(R.id.time_btn);

        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        //알림취소
        Button alarm_cancel_btn = findViewById(R.id.alarm_cancel_btn);
        alarm_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Log.d(TAG, "ONTIMESET");

        getUserWakeSleep();


        Calendar c = Calendar.getInstance();

//        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        c.set(Calendar.MINUTE, minute);
//        c.set(Calendar.SECOND, 0);
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour_sleep));
        c.set(Calendar.MINUTE, Integer.parseInt(minute_sleep));
        c.set(Calendar.SECOND, 0);

        //화면에 시간지정
        updateTimeText(c);

        //알람설정
        startAlarm(c);
    }

    private  void updateTimeText(Calendar c){
        Log.d(TAG, "UPDATETIMETEXT");
        String timeText = "알람시간: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        time_text.setText(timeText);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c){
        Log.d(TAG, "STARTALARM");
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    private void cancelAlarm(){
        Log.d(TAG, "CANCELALARM");
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        time_text.setText("알람취소");
    }

    // 유저의 기상시간과 수면시간을 받아옴
    public void getUserWakeSleep() {

        ArrayList<String> userInfo = new ArrayList<String>();

        UserDBHelper helper = new UserDBHelper(this);
        SQLiteDatabase userDB = helper.getReadableDatabase();
        Cursor cursor = userDB.rawQuery("SELECT sleep FROM " + helper.TABLE_NAME + ";", null);

        while(cursor.moveToNext()) {
            String sleep = cursor.getString(1);

            Log.d("확인하자2", "취침시간: " + sleep);
            userInfo.add(sleep);
        }

        cursor.close();
        helper.close();

        String sleep = userInfo.get(userInfo.size() - 1);

        Log.d("확인하자", "취침시간: " + sleep);

        int index2 = sleep.indexOf(":");


        minute_sleep = sleep.substring(sleep.length() - 2, sleep.length());
        hour_sleep = sleep.substring(0, index2);
    }
}