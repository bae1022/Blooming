package swcontest.dwu.blooming.userSetting;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {
    public int mCount;

    public FragmentAdapter(FragmentActivity fa, int count){
        super(fa);
        mCount = count; //생성할 프래그먼트 개수
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if(position == 0) return new UserNameActivity();
        else if(position == 1) return new UserBirthActivity();
        else if(position == 2) return new UserHomeActivity();
        else if(position == 3) return new UserPeriodActivity();
        else if(position == 4) return new UserSleepActivity();
        else return new UserPhoneActivity();
    }

    @Override
    public int getItemCount() {
        return 6;
    }

//    public int getRealPosition(int position){
//        return position % mCount;
//    }
}
