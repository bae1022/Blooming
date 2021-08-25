package swcontest.dwu.blooming.userSetting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import swcontest.dwu.blooming.R;

public class UserHomeActivity extends Fragment {
    EditText et_home;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_home, container, false);
        et_home = rootView.findViewById(R.id.et_home);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(this.getContext(), "UserHome" + et_home.getText().toString(), Toast.LENGTH_SHORT).show();
        outState.putString("userHome", et_home.getText().toString());
    }
}
