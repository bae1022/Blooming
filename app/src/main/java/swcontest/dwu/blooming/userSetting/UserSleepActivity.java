package swcontest.dwu.blooming.userSetting;

import android.os.Bundle;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_sleep, container, false);

        et_wake_hour = rootView.findViewById(R.id.et_wake_hour);
        et_wake_minute = rootView.findViewById(R.id.et_wake_minute);
        et_sleep_hour = rootView.findViewById(R.id.et_sleep_hour);
        et_sleep_minute = rootView.findViewById(R.id.et_sleep_minute);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //일어나는 시간
        outState.putString("wake_hour", et_wake_hour.getText().toString());
        outState.putString("wake_minute", et_wake_minute.getText().toString());
        //자는 시간
        outState.putString("sleep_hour", et_sleep_hour.getText().toString());
        outState.putString("sleep_minute", et_sleep_minute.getText().toString());

    }
}
