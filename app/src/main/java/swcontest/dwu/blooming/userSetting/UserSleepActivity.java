package swcontest.dwu.blooming.userSetting;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import swcontest.dwu.blooming.R;
import swcontest.dwu.blooming.db.UserDBHelper;

public class UserSleepActivity extends Fragment {
    EditText et_wake_hour, et_wake_minute;
    EditText et_sleep_hour, et_sleep_minute;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_sleep, container, false);

        et_wake_hour = rootView.findViewById(R.id.et_wake_hour);
        et_wake_minute = rootView.findViewById(R.id.et_wake_minute);
        et_sleep_hour = rootView.findViewById(R.id.et_sleep_hour);
        et_sleep_minute = rootView.findViewById(R.id.et_sleep_minute);

//        UserNameActivity frag_4 = new UserNameActivity();
//        bundle = frag_4.bundle;
//        if(bundle != null)
//            Log.d("UserSleep(onCreateView)", "frag4에서 번들 가져옴");

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        UserNameActivity frag_4 = new UserNameActivity();
        bundle = frag_4.bundle;
        if(bundle != null)
            Log.d("UserSleep(onCreateView)", "frag4에서 번들 가져옴");
    }

    @Override
    public void onPause() {
        super.onPause();
        if(bundle != null){
            Log.d("UserSleep(onStop)", "취침/기상시간 번들에 담기");
            bundle.putString("wake_hour", et_wake_hour.getText().toString());
            bundle.putString("wake_minute", et_wake_minute.getText().toString());
            Log.d("UserSleep(onStop)", "기상시간: " + et_wake_hour.getText().toString()  +":" + et_wake_minute.getText().toString());
            bundle.putString("sleep_hour", et_sleep_hour.getText().toString());
            bundle.putString("sleep_minute", et_sleep_minute.getText().toString());
            Log.d("UserSleep(onStop)", "취침시간: " + et_sleep_hour.getText().toString()  +":" + et_sleep_minute.getText().toString());

        }
    }

}