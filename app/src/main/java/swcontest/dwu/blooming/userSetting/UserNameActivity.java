package swcontest.dwu.blooming.userSetting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import swcontest.dwu.blooming.R;

public class UserNameActivity extends Fragment {
    EditText et_name;

    //프래그먼트 처음 시작하는 생명주기
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_name, container, false);

//        StartActivity activity = (StartActivity) getActivity();
//        Bundle args = activity.bundle;
//
//        UserData user = new UserData();
        et_name = rootView.findViewById(R.id.et_name);
//        user.setName(et_name.getText().toString());
//        args.putSerializable("user", user);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //현재 프래그먼트의 데이터 저장
        Toast.makeText(this.getContext(),"UserName액티비티" + et_name.getText().toString(), Toast.LENGTH_SHORT).show();
        outState.putString("userName", et_name.getText().toString());
    }
}
