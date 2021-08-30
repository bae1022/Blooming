package swcontest.dwu.blooming.userSetting;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import swcontest.dwu.blooming.R;
import swcontest.dwu.blooming.db.UserDBHelper;

public class UserUpdateActivity extends AppCompatActivity {
    EditText db_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_update);

        db_name = findViewById(R.id.db_name);

        UserDBHelper helper = new UserDBHelper(this);
        SQLiteDatabase userDB = helper.getReadableDatabase();
        Cursor cursor = userDB.rawQuery("SELECT name, address FROM " + helper.TABLE_NAME + ";", null);

        while(cursor.moveToNext()) {
            String name = cursor.getString(0);
            db_name.setText(name);
            String address = cursor.getString(1);
            Toast.makeText(this, address+ "사는 " +name + "님 안녕하세요!", Toast.LENGTH_LONG).show();
        }

    }

}
