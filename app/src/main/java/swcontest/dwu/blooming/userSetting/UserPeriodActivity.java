package swcontest.dwu.blooming.userSetting;

import android.os.Bundle;
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

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(this.getContext(), "UserPeriod"+ sp_period.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        outState.putString("userPeriod", sp_period.getSelectedItem().toString());
    }
}
