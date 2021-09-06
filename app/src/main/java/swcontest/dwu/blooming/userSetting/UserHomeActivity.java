package swcontest.dwu.blooming.userSetting;

import android.os.Bundle;
import android.util.Log;
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
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_home, container, false);
        et_home = rootView.findViewById(R.id.et_home);
        //Bundle 가져오기
//        UserNameActivity frag_3 = new UserNameActivity();
//        bundle = frag_3.bundle;
//        if(bundle != null)
//            Log.d("UserHome(onCreate)", "번들있음");

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        UserNameActivity frag_3 = new UserNameActivity();
        bundle = frag_3.bundle;
        if(bundle != null)
            Log.d("UserHome(onCreate)", "번들있음");
    }

    @Override
    public void onPause() {
        super.onPause();
        if(bundle != null){
            Log.d("UserHome(onStop)", "집 주소 번들에 담기");
            bundle.putString("userHome", et_home.getText().toString());
            Log.d("UserHome(onStop)", "집 주소: " + et_home.getText().toString());
        }
    }
}