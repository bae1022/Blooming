package swcontest.dwu.blooming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
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

    public static String minute_wake;
    public static String hour_wake;
    public static String minute_sleep;
    public static String hour_sleep;
    public static int period = 5;
    String tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //사용자 초기설정 db여부에 따른 첫화면 변경
        File userFolder = new File(String.valueOf(getDatabasePath("user.db"))); //읽고자 하는 파일 경로
        Log.d("MainActivity", "user.db경로 확인:" + String.valueOf(getDatabasePath("user.db")));

        if(!userFolder.exists()){
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
        } else {

            getUserWakeSleep(); // 사용자의 취침, 기상 시각을 받아온다.
            getPeriod();

            Toast.makeText(getApplicationContext(), "Service 시작", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, DailyMemoService.class);
            startService(intent);

            //알람 종료
//        Intent intent = new Intent(MainActivity.this,DailyMemoService.class);
//        stopService(intent);

            //매 12시 일상기록 없어지도록 함
            resetDailyMemo(this);

            Log.d("LoactionService", "Location Service 시작");
            Intent location_intent = new Intent(MainActivity.this, LocationService.class);
            startService(location_intent);

            if (!isGPSEnabled()) {
                buildAlertMessageNoGps();
            }
        }

        checkDangerousPermissions();

        //보호자 전화 연동
        ImageView iv_siren = findViewById(R.id.iv_siren);
        iv_siren.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int check_permission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CALL_PHONE);
                if(check_permission == PackageManager.PERMISSION_DENIED){   //권한이 없는 경우
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("권한이 필요합니다.")
                                    .setMessage("이 기능을 사용하기 위해 \"전화걸기\" 권한이 필요합니다. 계속 하시겠습니까?")
                                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                            }
                                        }
                                    })
                                    .setNegativeButton("아니요", null)
                                    .create()
                                    .show();
                        } else{   //최초로 권한을 요청할 때
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000); //OS에 요청
                        }
                    }
                }else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("긴급 연락")
                            .setMessage("보호자에게 바로 전화가 연결됩니다.\n계속 하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    UserDBHelper helper = new UserDBHelper(getApplication());
                                    SQLiteDatabase userDB = helper.getReadableDatabase();
                                    Cursor cursor = userDB.rawQuery("SELECT phone FROM " + helper.TABLE_NAME + ";", null);
                                    if(cursor.moveToNext()){
                                        tel = "tel:" + cursor.getString(cursor.getColumnIndex(helper.COL_PHONE));
                                        Log.d("MainActivity", tel);
                                    }
                                    Toast.makeText(getApplication(), "전화가 연결됩니다", Toast.LENGTH_SHORT).show();
                                    cursor.close();
                                    helper.close();
                                    startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
                                }
                            })
                            .setNegativeButton("아니요", null)
                            .create()
                            .show();
                }
            }
        });
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
                intent = new Intent(this, DiagnosisActivity.class);
                break;

//            case R.id.btn_setting: //초기화면
//                intent = new Intent(this, StartActivity.class);
//                break;

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

    public void getPeriod() {
        UserDBHelper helper = new UserDBHelper(getApplicationContext());
        SQLiteDatabase userDB = helper.getReadableDatabase();
        Cursor cursor = userDB.rawQuery("SELECT period FROM " + helper.TABLE_NAME + ";", null);
        if (cursor.moveToNext()){
            period = cursor.getInt(cursor.getColumnIndex(helper.COL_PERIOD));
            Log.d("Location", "DB에서 받아온 주기: " + period );
        }
        cursor.close();
        helper.close();
    }

    // 유저의 기상시간과 수면시간을 받아옴
    public void getUserWakeSleep() {

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

        String wake = userInfo.get(userInfo.size() - 2);
        String sleep = userInfo.get(userInfo.size() - 1);

        Log.d("확인하자", "기상시간: " + wake);
        Log.d("확인하자", "취침시간: " + sleep);

        int index = wake.indexOf(":");
        int index2 = sleep.indexOf(":");

        minute_wake = wake.substring(wake.length() - 2, wake.length());
        hour_wake = wake.substring(0, index);

        minute_sleep = sleep.substring(sleep.length() - 2, sleep.length());
        hour_sleep = sleep.substring(0, index2);
    }

    public void buildAlertMessageNoGps() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("GPS(위치) 사용 유무 셋팅");
        alertDialog.setMessage("GPS(위치) 셋팅이 되지 않았을수도 있습니다. \n 설정창으로 가시겠습니까?");

        // OK 를 누르게 되면 설정창으로 이동
        alertDialog.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent enableGpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(enableGpsIntent);
                    }
                });

        alertDialog.show();
    }

    public boolean isGPSEnabled() {
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }
}