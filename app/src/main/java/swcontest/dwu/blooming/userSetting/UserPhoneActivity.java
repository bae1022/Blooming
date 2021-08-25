package swcontest.dwu.blooming.userSetting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import swcontest.dwu.blooming.MainActivity;
import swcontest.dwu.blooming.R;

public class UserPhoneActivity extends Fragment {
    EditText et_phone;
    private UserData user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("UserPhoneActivity", "onCreateView화면 안");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_phone, container, false);

        et_phone = rootView.findViewById(R.id.et_phone);
        user = new UserData();

        Button btn_done = rootView.findViewById(R.id.btn_done);

        btn_done.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean check = true;

//                if(savedInstanceState.getString("userName") != null){
//                    Toast.makeText(getActivity(), savedInstanceState.getString("userName").toString(), Toast.LENGTH_SHORT).show();
//                    user.setName(savedInstanceState.get("userName").toString());
//                } else { check=false;
//                Toast.makeText(getActivity(), "사용자 이름을 확인해 주세요", Toast.LENGTH_SHORT).show(); }

//                if(savedInstanceState.get("userBirth") != null) {
//                    String str = savedInstanceState.get("userBirth").toString();
//                    String[] arr = str.split("/");
//                    user.setYear(Integer.parseInt(arr[0]));
//                    user.setMonth(Integer.parseInt(arr[1]));
//                    user.setDay(Integer.parseInt(arr[2]));
//                } else { check=false; Toast.makeText(getActivity(), "생년월일을 확인해 주세요", Toast.LENGTH_SHORT).show(); }
//
//                if(savedInstanceState.get("userHome") != null){
//                    user.setAddress(savedInstanceState.get("userHome").toString());
//                } else{ check=false; Toast.makeText(getActivity(), "주소를 확인해 주세요", Toast.LENGTH_SHORT).show(); }
//
//                if(savedInstanceState.get("userPeriod") != null){
//                    user.setPeriod((int)savedInstanceState.get("userPeriod"));
//                } else{ check=false; Toast.makeText(getActivity(), "기록 주기를 확인해 주세요", Toast.LENGTH_SHORT).show(); }
//
//                if(savedInstanceState.get("wake_hour") != null && savedInstanceState.get("wake_minute") != null){
//                    SimpleDateFormat sd = new SimpleDateFormat("HH시 mm분");
//                    Date wake = new Date();
//                    sd.format(wake);
//                    user.setWakeTime(wake);
//                } else{ check=false; Toast.makeText(getActivity(), "기상 시각을 확인해 주세요", Toast.LENGTH_SHORT).show(); }
//
//                if(savedInstanceState.get("sleep_hour") != null && savedInstanceState.get("sleep_minute") != null){
//                    SimpleDateFormat sd = new SimpleDateFormat("HH시 mm분");
//                    Date sleep = new Date();
//                    sd.format(sleep);
//                } else{ check=false; Toast.makeText(getActivity(), "취침 시간을 확인해 주세요", Toast.LENGTH_SHORT).show(); }
//
//                //추후 핸드폰번호 패턴오류도 확인하기
//                if(et_phone.getText().toString() != ""){
//                    user.setPhone(et_phone.getText().toString());
//                } else{ check=false; Toast.makeText(getActivity(), "핸드폰 번호를 확인해 주세요", Toast.LENGTH_SHORT).show(); }

                if(check){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("환영합니다")
                            .setMessage("사용자 초기설정이 완료되었습니다. \n 환영합니다")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }

                }
            }
        );

        return rootView;
    }

}
