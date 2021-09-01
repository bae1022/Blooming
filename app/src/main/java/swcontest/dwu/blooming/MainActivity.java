package swcontest.dwu.blooming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import swcontest.dwu.blooming.db.UserDBHelper;
import swcontest.dwu.blooming.service.DailyMemoService;
import swcontest.dwu.blooming.service.LocationService;
import swcontest.dwu.blooming.userSetting.StartActivity;
import swcontest.dwu.blooming.userSetting.UserUpdateActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

    //일상 기록 Notification 설정_개발하실 때 주석처리 해주세요
//        Toast.makeText(getApplicationContext(),"Service 시작", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(MainActivity.this, DailyMemoService.class);
//        startService(intent);

        //알람 종료
//        Intent intent = new Intent(MainActivity.this,DailyMemoService.class);
//        stopService(intent);

        //매 12시 일상기록 없어지도록 함
        resetDailyMemo(this);

        alarmDailyMemo(this);

        Intent lintent = new Intent(MainActivity.this, LocationService.class);
        startService(lintent);
        Log.d("LoactionService", "Location Service 시작");
    }

    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btn_location: // 위치 기록
                intent = new Intent(this, LocationActivity.class);
                break;

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

            case R.id.btn_setting: //초기화면
                intent = new Intent(this, StartActivity.class);
                break;

            case R.id.btn_game:
                intent = new Intent(this, GameActivity.class);

                Toast.makeText(this, " 눌리긴 함", Toast.LENGTH_LONG).show();
                break;
        }
        if (intent != null)
            startActivity(intent);

    }

    //위험 권한 체크
    //manifest와 java에 둘 다 권한 허가받는 코드를 작성한다.
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                    if (i == permissions.length - 1) {
                        Log.d("LoactionService", "Location Service 시작");
                        Intent location_intent = new Intent(MainActivity.this, LocationService.class);
                        startService(location_intent);
                    }
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void resetDailyMemo(Context context){
        AlarmManager resetAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyMemoResetBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        // 자정 시간
        Calendar resetCal = Calendar.getInstance();
        resetCal.setTimeInMillis(System.currentTimeMillis());
        resetCal.set(Calendar.HOUR_OF_DAY, 0);
        resetCal.set(Calendar.MINUTE,0);
        resetCal.set(Calendar.SECOND, 0);

//        //다음날 0시에 맞추기 위해 24시간을 뜻하는 상수인 AlarmManager.INTERVAL_DAY를 더해줌.
        resetAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, resetCal.getTimeInMillis() + AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, sender);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd kk:mm:ss");
        String setResetTime = format.format(new Date(resetCal.getTimeInMillis() + AlarmManager.INTERVAL_DAY));
        Log.d("resetAlarm", "ResetHour : " + setResetTime);
    }

    //액션바 설정버튼 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btn_setting:
                Intent intent = new Intent(MainActivity.this, UserUpdateActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    public void alarmDailyMemo(Context context){
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmDailyMemoBroadcastReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(context, 1, intent, 0);

            ArrayList<String> userInfo = new ArrayList<String>();

            userInfo = getUserWakeSleep();

            String wake = userInfo.get(0);
            String sleep = userInfo.get(1);

            Log.d("확인하자", "기상시간: " + wake);
        Log.d("확인하자", "취침시간: " + sleep);

        int index = wake.indexOf(":");
        int index2 = sleep.indexOf(":");

        String minute_wake = wake.substring(wake.length() - 2, wake.length());
        String hour_wake = wake.substring(0, index);


        String minute_sleep = sleep.substring(sleep.length() - 2, sleep.length());
        String hour_sleep = sleep.substring(0, index2);

        Log.d(".", hour_wake);
        Log.d(".", minute_wake);
        Log.d(".", hour_sleep);
        Log.d(".", minute_sleep);

            Calendar alarmCal = Calendar.getInstance();

        alarmCal.setTimeInMillis(System.currentTimeMillis());
        alarmCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour_wake));
        alarmCal.set(Calendar.MINUTE, Integer.valueOf(minute_wake));
        alarmCal.set(Calendar.SECOND, 0);

// 1000*60*60*4

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("hh");
        String getTime = simpleDate.format(mDate);

        int getH = Integer.valueOf(getTime);

        Log.d("확인", getTime);
        Log.d("확인", hour_wake);
        Log.d("확인", hour_sleep);

        if (Integer.parseInt(hour_wake) <= getH && Integer.parseInt(hour_sleep) >= getH){ //조건 더 설정해야함
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), 30, sender);
        }
        else{
            alarmManager.cancel(sender);
        }

    }

    // 유저의 기상시간과 수면시간을 받아옴
    public ArrayList<String> getUserWakeSleep() {

        ArrayList<String> userInfo = new ArrayList<String>();

        UserDBHelper helper = new UserDBHelper(this);
        SQLiteDatabase userDB = helper.getReadableDatabase();
        Cursor cursor = userDB.rawQuery("SELECT wake, sleep FROM " + helper.TABLE_NAME + ";", null);

        while(cursor.moveToNext()) {
            String wake = cursor.getString(0);
            String sleep = cursor.getString(1);

            Log.d("확인하자2", "기상시간: " + wake);
            Log.d("확인하자2", "취침시간: " + sleep);

            userInfo.add(wake);
            userInfo.add(sleep);
        }

        cursor.close();
        helper.close();

        return userInfo;
    }
}