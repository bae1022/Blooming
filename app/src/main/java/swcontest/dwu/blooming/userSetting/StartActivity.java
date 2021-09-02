package swcontest.dwu.blooming.userSetting;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

import swcontest.dwu.blooming.R;

public class StartActivity extends FragmentActivity{   //extends AppCompatActivity {

    //Fragment위해 추가
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 6;

    //데이터 가져오기 위해
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_start);

        mPager = findViewById(R.id.viewPager2);
        pagerAdapter = new FragmentAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);

//        mPager.setCurrentItem(3); //초기화면 선택
        mPager.setOffscreenPageLimit(1);  // 좌우로 6페이지를 그려져 있는 상태로 설정

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if(positionOffsetPixels == 0)
                    mPager.setCurrentItem(position);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("StartActivity(Destroy)", "프래그먼트 액티비티 종료");

        // Frag_1의 Bundel을 갖고오기
        UserNameActivity frag_1 = new UserNameActivity();
        bundle = frag_1.bundle;
        if(bundle != null)
            Log.d("Start(onDestroy)", "번들 가져옴");

        Log.d("Start(onDestroy)", "최종 번들값 확인");
        Log.d("Start(onDestroy)", "사용자 이름:" + bundle.getString("userName"));
        Log.d("Start(onDestroy)", "년월일:" + bundle.getInt("userYear") + "/" + bundle.getInt("userMonth")+"/"+ bundle.getInt("userDay"));
        Log.d("Start(onDestroy)", "집주소:" + bundle.getString("userHome"));
        Log.d("Start(onDestroy)", "추적 주기:" + bundle.getString("userPeriod"));
        Log.d("Start(onDestroy)", "기상시간: " + bundle.getString("wake_hour") +":"+bundle.getString("wake_minute"));
        Log.d("Start(onDestroy)", "취침시간: " + bundle.getString("sleep_hour") +":"+bundle.getString("sleep_minute"));
//        Log.d("UserPhone(onSave)", "비상연락망:" + et_phone.getText().toString());
    }


//    @Override
//    protected void onStop() {
//        super.onStop();
//        UserNameActivity frag = new UserNameActivity();
//        bundle = frag.bundle;
//        if(bundle != null) {
//            Log.d("StartActivity(onStop)", "UserName의 번들 존재, 가져옴");
//            Log.d("StartActivity(onStop)", "진짜 최종 번들값 확인");
//            Log.d("StartActivity(onStop)", "사용자 이름:" + bundle.getString("userName"));
//            Log.d("StartActivity(onStop)", "년월일:" + bundle.getInt("userYear") + "/" + bundle.getInt("userMonth")+"/"+ bundle.getInt("userDay"));
//            Log.d("StartActivity(onStop)", "집주소:" + bundle.getString("userHome"));
//            Log.d("StartActivity(onStop)", "추적 주기:" + bundle.getString("userPeriod"));
//            Log.d("StartActivity(onStop)", "기상시간: " + bundle.getString("wake_hour") +":"+bundle.getString("wake_minute"));
//            Log.d("StartActivity(onStop)", "취침시간: " + bundle.getString("sleep_hour") +":"+bundle.getString("sleep_minute"));
////            Log.d("UserPhone(onSave)", "비상연락망:" + et_phone.getText().toString());
//        }
//
//    }


}
