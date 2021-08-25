package swcontest.dwu.blooming.userSetting;

import android.os.Bundle;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.get("userName") != null){
            Toast.makeText(this.getContext(),"값 존재:" + savedInstanceState.get("userName"), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this.getContext(),"UserBirth액티비티", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this.getContext(), "w" + savedInstanceState.get("userName") , Toast.LENGTH_SHORT).show();

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_birth, container, false);

        dp_birth = rootView.findViewById(R.id.datePicker);

        StartActivity activity = (StartActivity) getActivity();
        Bundle args = activity.bundle;
        TextView tv_user = rootView.findViewById(R.id.tv_user);

        Calendar calendar = new GregorianCalendar();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePicker dp = rootView.findViewById(R.id.datePicker);
        dp.init(mYear, mMonth, mDay, mOnDateChangedListener);

        if(args != null){
            UserData user = (UserData) args.getSerializable("user");
            tv_user.setText(user.getName());
            user.setYear(mYear);
            user.setMonth(mMonth);
            user.setDay(mDay);
            args.putSerializable("user", user);
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(this.getContext(), "UserBirth액티비티" + dp_birth.getYear(), Toast.LENGTH_SHORT).show();
        String birth = dp_birth.getYear() + "/" + dp_birth.getMonth() + "/" + dp_birth.getDayOfMonth();
        outState.putString("userBirth", birth);
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
