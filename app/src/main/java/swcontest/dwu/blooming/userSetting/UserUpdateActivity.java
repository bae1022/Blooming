package swcontest.dwu.blooming.userSetting;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import swcontest.dwu.blooming.R;
import swcontest.dwu.blooming.db.UserDBHelper;

public class UserUpdateActivity extends AppCompatActivity {
    EditText db_name, db_year, db_month, db_day;
    EditText db_address, db_period, db_wake, db_sleep, db_phone;
    Button btn_update, btn_ok;

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

//            Toast.makeText(this, address+ "사는 " +name + "님 안녕하세요!", Toast.LENGTH_LONG).show();
        }

    }

}
