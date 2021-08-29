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
//    ViewPager2 viewPager2;
//    Button btnToggle;

    //Fragment위해 추가
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 6;

    //데이터 전달 위해
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

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_start);

        Log.d("StartActivity", "1");
        viewPager2 = findViewById(R.id.viewPager2);
        btnToggle = findViewById(R.id.btnToggle);

        ArrayList<UserData> list = new ArrayList<>();
        list.add(new UserData(1, "1 page"));
        list.add(new UserData(2, "2page"));
        list.add(new UserData(1, "3page"));
        list.add(new UserData(2, "4page"));

        viewPager2.setAdapter(new ViewPagerAdapter(list));

        btnToggle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(viewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL){
                    btnToggle.setText("가로버전");
                    viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                } else{
                    btnToggle.setText("세로버전");
                    viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                }
            }
        });
    }

    */
}
