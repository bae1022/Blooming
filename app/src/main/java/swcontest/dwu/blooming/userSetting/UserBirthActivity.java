package swcontest.dwu.blooming.userSetting;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

import swcontest.dwu.blooming.R;

public class UserBirthActivity extends Fragment {
    private int mYear, mMonth, mDay;
    DatePicker dp_birth;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_birth, container, false);

        //DatePicker 현재 날짜로 띄우기
        Calendar calendar = new GregorianCalendar();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePicker dp = rootView.findViewById(R.id.datePicker);
        dp.init(mYear, mMonth, mDay, mOnDateChangedListener);

        dp_birth = rootView.findViewById(R.id.datePicker);

        // Frag_1의 Bundel을 갖고오기
        UserNameActivity frag_1 = new UserNameActivity();
        bundle = frag_1.bundle;
        if(bundle != null)
            Log.d("UserBirth(onCreateView)", "frag_1에서 번들 가져옴");

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(bundle != null){
            Log.d("UserBirth(onSave)", "날짜 값 번들에 담기");
            bundle.putInt("userYear", dp_birth.getYear());
            bundle.putInt("userMonth", dp_birth.getMonth());
            bundle.putInt("userDay", dp_birth.getDayOfMonth());
            Log.d("UserBirth(onSave)", "년월일: " + dp_birth.getYear() + "/" + dp_birth.getMonth() + "/" + dp_birth.getDayOfMonth());
        }
    }

    DatePicker.OnDateChangedListener mOnDateChangedListener = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker datePicker, int y, int m, int d) {
            mYear = y;
            mMonth = m;
            mDay = d;
        }
    };
}
