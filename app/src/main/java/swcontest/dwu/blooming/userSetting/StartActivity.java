package swcontest.dwu.blooming.userSetting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

import swcontest.dwu.blooming.R;

public class StartActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    Button btnToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_start);

        viewPager2 = findViewById(R.id.viewPager2);
        btnToggle = findViewById(R.id.btnToggle);

        ArrayList<UserData> list = new ArrayList<>();
        list.add(new UserData(android.R.color.holo_blue_bright, "1page"));
        list.add(new UserData(android.R.color.holo_blue_dark, "2page"));
        list.add(new UserData(android.R.color.holo_blue_bright, "3page"));
        list.add(new UserData(android.R.color.holo_blue_dark, "4page"));

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

}
