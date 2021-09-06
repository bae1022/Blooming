package swcontest.dwu.blooming.userSetting;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import swcontest.dwu.blooming.MainActivity;
import swcontest.dwu.blooming.R;
import swcontest.dwu.blooming.db.UserDBHelper;

public class UserUpdateActivity extends AppCompatActivity{

    public  static  final String TAG = "UPDATE_Broadcast";

    EditText db_name, db_year, db_month, db_day;
    EditText db_address, db_period, db_wake, db_sleep, db_phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_update);

        db_name = findViewById(R.id.db_name);
        db_year = findViewById(R.id.db_year);
        db_month = findViewById(R.id.db_month);
        db_day = findViewById(R.id.db_day);
        db_address = findViewById(R.id.db_address);
        db_period = findViewById(R.id.db_period);
        db_wake = findViewById(R.id.db_wake);
        db_sleep = findViewById(R.id.db_sleep);
        db_phone = findViewById(R.id.db_phone);

        UserDBHelper helper = new UserDBHelper(this);
        SQLiteDatabase userDB = helper.getReadableDatabase();
        Cursor cursor = userDB.rawQuery("SELECT * FROM " + helper.TABLE_NAME + ";", null);

        while(cursor.moveToNext()) {
            db_name.setText(cursor.getString(1));   //name
            db_year.setText( String.valueOf(cursor.getInt(2)) );
            db_month.setText( String.valueOf(cursor.getInt(3)) );
            db_day.setText( String.valueOf(cursor.getInt(4)) );
            db_address.setText(cursor.getString(5));
            db_period.setText( String.valueOf(cursor.getInt(6)) );
            db_wake.setText(cursor.getString(7));
            db_sleep.setText(cursor.getString(8));
            db_phone.setText(cursor.getString(9));
        }
        cursor.close();
        helper.close();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_ok:
                Intent intent = new Intent(UserUpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_update:
                //DB 업데이트 작업
                UserDBHelper helper = new UserDBHelper(this);
                SQLiteDatabase userDB = helper.getWritableDatabase();

                ContentValues row = new ContentValues();
                row.put("name", db_name.getText().toString());
                row.put("year", Integer.parseInt(db_year.getText().toString()));
                row.put("month", Integer.parseInt(db_month.getText().toString()));
                row.put("day", Integer.parseInt(db_day.getText().toString()));
                row.put("address", db_address.getText().toString());
                row.put("period", Integer.parseInt(db_period.getText().toString()));
                row.put("wake", db_wake.getText().toString());
                row.put("sleep", db_sleep.getText().toString());
                row.put("phone", db_phone.getText().toString());
                String whereClause = helper.COL_ID + "=?";
                String[] whereArgs = new String[] {String.valueOf(1)};
                int result = userDB.update(helper.TABLE_NAME, row, whereClause, whereArgs);
                helper.close();

                if(result > 0) {
                    Toast.makeText(this.getApplicationContext(), "사용자 정보가 변경되었습니다", Toast.LENGTH_SHORT).show();
                    finish();
                } else{
                    Toast.makeText(this.getApplicationContext(), "제대로 입력했는지 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                Intent nintent = new Intent(UserUpdateActivity.this, MainActivity.class);
                startActivity(nintent);
                finish();
                break;
        }
    }


}
