package swcontest.dwu.blooming.userSetting;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import swcontest.dwu.blooming.R;
import swcontest.dwu.blooming.db.UserDBHelper;

public class UserNameActivity extends Fragment {
    EditText et_name;
    String name;
    static Bundle bundle;

    //프래그먼트 처음 시작하는 생명주기
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_name, container, false);

        //데이터 전달
        et_name = rootView.findViewById(R.id.et_name);
//        bundle = new Bundle();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        bundle = new Bundle();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(et_name.getText().toString().equals("")){
            //입력 필드가 공백일 때 작동 안함
            Log.d("UserName(onStop)", "et값이 없다");
        } else{
            Log.d("UserName(onStop)", "et값이 있다" + et_name.getText().toString());
            bundle.putString("userName", et_name.getText().toString());
        }
    }

    /* 이거는 한 프래그먼트 내부에서만 사용되는 Bundle인 것 같음
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //현재 프래그먼트의 데이터 저장
//        ContentValues row = new ContentValues();
//        row.put("name", et_name.getText().toString());
//        db.insert()
        //프래그먼트 데이터 전달을 하고 싶었는데 무슨 짓을 한건진 잘 모르겠음
//        Fragment frag_2 = new UserPhoneActivity();         //프래그먼트 생성
//        Bundle bundle = new Bundle();              // 파라미터: 전달한 데이터 개수
//        bundle.putString("userName", et_name.getText().toString());
//        frag_2.setArguments(bundle);
//        Log.d("UserName액티비티(onSave): ", et_name.getText().toString() + "를 번들에 넣음4");
        Toast.makeText(this.getContext(),"UserName액티비티" + et_name.getText().toString(), Toast.LENGTH_SHORT).show();
        outState.putString("userName", et_name.getText().toString());
    }
    */
}