package swcontest.dwu.blooming.userSetting;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import swcontest.dwu.blooming.MainActivity;
import swcontest.dwu.blooming.R;
import swcontest.dwu.blooming.db.UserDBHelper;

public class UserPhoneActivity extends Fragment {
    EditText et_phone;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("UserPhoneActivity", "onCreateView화면 안");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_phone, container, false);

        et_phone = rootView.findViewById(R.id.et_phone);

//        //Bundle가져오기
//        UserNameActivity frag_5 = new UserNameActivity();
//        bundle = frag_5.bundle;
//        if(bundle != null){
//            Log.d("UserPhone(onCreateView)", "frag_5에서 번들 가져옴");
//        }

        Button btn_done = rootView.findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new Button.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            boolean check = true;
                                            Log.d("UserPhone(onCreateView)", "onClick");
//                Log.d("UserPhone(onCreateView)", "사용자 이름:" + bundle.getString("userName"));
//                Log.d("UserPhone(onCreateView)", "년월일:" + bundle.getString("userYear") + "/" + bundle.getString("userMonth")+"/"+ bundle.getString("userDay"));
//                Log.d("UserPhone(onCreateView)", "집주소:" + bundle.getString("userHome"));
//                Log.d("UserPhone(onCreateView)", "추적 주기:" + bundle.getString("userPeriod"));
//                Log.d("UserPhone(onCreateView)", "기상시간: " + bundle.getString("wake_hour") +":"+bundle.getString("wake_minute"));
//                Log.d("UserPhone(onCreateView)", "취침시간: " + bundle.getString("sleep_hour") +":"+bundle.getString("sleep_minute"));
//                Log.d("UserPhone(onCreateView)", "비상연락망:" + et_phone.getText().toString());

                                            if (bundle.getString("userName") == null || bundle.getString("userName").equals("")){
                                                StartActivity.fragmentChange(0);
                                                Toast.makeText(getActivity(), "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
                                            }
                                            else if (bundle.getString("userHome") == null || bundle.getString("userHome").equals("")){
                                                StartActivity.fragmentChange(2);
                                                Toast.makeText(getActivity(), "주소를 입력해주세요!", Toast.LENGTH_SHORT).show();
                                            }
                                            else if (bundle.getString("wake_hour") == null || bundle.getString("wake_hour").equals("")
                                            || bundle.getString("wake_minute") == null || bundle.getString("wake_minute").equals("")
                                            || bundle.getString("sleep_hour") == null || bundle.getString("sleep_hour").equals("")
                                            || bundle.getString("sleep_minute") == null || bundle.getString("sleep_minute").equals("") ){
                                                StartActivity.fragmentChange(4);
                                                Toast.makeText(getActivity(), "기상 및 수면 시간을 입력해주세요!", Toast.LENGTH_SHORT).show();
                                            }
                                            else if ( et_phone.getText().toString().equals("")){
                                                Toast.makeText(getActivity(), "보호자 번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                if (check) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                    builder.setTitle("환영합니다^^")
                                                            .setMessage("사용자 초기설정이 완료되었습니다. \n개화와 함께 하루를 만들어 가보세요!")
                                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    getActivity().finish();
//                                    Intent intent = new Intent(getContext(), MainActivity.class);
//                                    startActivity(intent);
                                                                }
                                                            })
                                                            .show();
                                                }
                                            }
                                        }
                                    }
        );

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Bundle가져오기
        UserNameActivity frag_5 = new UserNameActivity();
        bundle = frag_5.bundle;
        if(bundle != null){
            Log.d("UserPhone(onCreateView)", "frag_5에서 번들 가져옴");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(bundle != null){
            Log.d("UserPhone(onStop)", "사용자 번호 번들에 담음");
            bundle.putString("userPhone", et_phone.getText().toString());
            Log.d("UserPhone(onStop)", "보호자 번호: " + et_phone.getText().toString());
        }

        Log.d("UserPhone(onStop)", "---최종 번들값 확인---");
        Log.d("UserPhone(onStop)", "사용자 이름:" + bundle.getString("userName"));
        Log.d("UserPhone(onStop)", "년월일:" + bundle.getInt("userYear") + "/" + bundle.getInt("userMonth")+"/"+ bundle.getInt("userDay"));
        Log.d("UserPhone(onStop)", "집주소:" + bundle.getString("userHome"));
        Log.d("UserPhone(onStop)", "추적 주기:" + bundle.getString("userPeriod"));
        Log.d("UserPhone(onStop)", "기상시간: " + bundle.getString("wake_hour") +":"+bundle.getString("wake_minute"));
        Log.d("UserPhone(onStop)", "취침시간: " + bundle.getString("sleep_hour") +":"+bundle.getString("sleep_minute"));
        Log.d("UserPhone(onStop)", "비상연락망:" + et_phone.getText().toString());

        //DB저장
        UserDBHelper helper = new UserDBHelper(this.getContext());
        SQLiteDatabase userDB = helper.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put("name", bundle.getString("userName"));
        row.put("year", bundle.getInt("userYear"));
        row.put("month", bundle.getInt("userMonth"));
        row.put("day", bundle.getInt("userDay"));
        row.put("address", bundle.getString("userHome"));
        if(bundle.getString("userPeriod") != null){
            row.put("period", Integer.parseInt(bundle.getString("userPeriod")));
        } else{
            row.put("period", 50);
        }
        row.put("wake", bundle.getString("wake_hour") + ":" + bundle.getString("wake_minute"));
        row.put("sleep", bundle.getString("sleep_hour") +":"+bundle.getString("sleep_minute"));
        row.put("phone", et_phone.getText().toString());

        userDB.insert(helper.TABLE_NAME, null, row);
        helper.close();

    }

}