package swcontest.dwu.blooming.userSetting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import swcontest.dwu.blooming.R;

public class UserPhoneActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("UserPhoneActivity", "onCreateView화면 안");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_phone, container, false);
        return rootView;
    }

}
