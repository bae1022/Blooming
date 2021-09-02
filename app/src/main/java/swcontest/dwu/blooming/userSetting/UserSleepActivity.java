package swcontest.dwu.blooming.userSetting;

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

        UserNameActivity frag_4 = new UserNameActivity();
        bundle = frag_4.bundle;
        if(bundle != null)
            Log.d("UserSleep(onCreateView)", "frag4에서 번들 가져옴");

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(bundle != null){
            Log.d("UserSleep(onSave)", "취침/기상시간 번들에 담기");
            bundle.putString("wake_hour", et_wake_hour.getText().toString());
            bundle.putString("wake_minute", et_wake_minute.getText().toString());
            Log.d("UserSleep(onSave)", "기상시간: " + et_wake_hour.getText().toString()  +":" + et_wake_minute.getText().toString());
            bundle.putString("sleep_hour", et_sleep_hour.getText().toString());
            bundle.putString("sleep_minute", et_sleep_minute.getText().toString());
            Log.d("UserSleep(onSave)", "취침시간: " + et_sleep_hour.getText().toString()  +":" + et_sleep_minute.getText().toString());

            Log.d("UserPhone(onSave)", "_____________________________번들확인 및 마지막 프래그먼트인가______________________________");
            Log.d("UserPhone(onSave)", "사용자 이름:" + bundle.getString("userName"));
            Log.d("UserPhone(onSave)", "년월일:" + bundle.getInt("userYear") + "/" + bundle.getInt("userMonth")+"/"+ bundle.getInt("userDay"));
            Log.d("UserPhone(onSave)", "집주소:" + bundle.getString("userHome"));
            Log.d("UserPhone(onSave)", "추적 주기:" + bundle.getString("userPeriod"));
            Log.d("UserPhone(onSave)", "기상시간: " + bundle.getString("wake_hour") +":"+bundle.getString("wake_minute"));
            Log.d("UserPhone(onSave)", "취침시간: " + bundle.getString("sleep_hour") +":"+bundle.getString("sleep_minute"));
            Log.d("UserPhone(onSave)", "비상연락망:" + bundle.getString("userPhone"));
        }



    }
}
