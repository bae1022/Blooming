package swcontest.dwu.blooming.userSetting;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;

import swcontest.dwu.blooming.R;

public class UserPeriodActivity extends Fragment {
    Spinner sp_period;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_period, container, false);
        Spinner sp = rootView.findViewById(R.id.spinner);
        Integer[] arr = {10, 20, 30, 40, 50, 60, 70};

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this.getContext(),
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp.setAdapter(adapter);

        sp_period = rootView.findViewById(R.id.spinner);

        UserNameActivity frag_3 = new UserNameActivity();
        bundle = frag_3.bundle;
        if(bundle != null)
            Log.d("UserPeriod(onCreate)", "frag_3 번들 가져옴");

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(bundle != null){
            Log.d("UserPeriod(onSave)", "위치추적 주기 번들에 담기");
            bundle.putString("userPeriod", sp_period.getSelectedItem().toString());
            Log.d("UserPeriod(onSave)", "주기: " + sp_period.getSelectedItem().toString());
            Log.d("UserPeriod(Test)", "이쯤에서 이름값 있는지 확인:" + bundle.getString("userName"));
        }
//        Toast.makeText(this.getContext(), "UserPeriod"+ sp_period.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }
}
