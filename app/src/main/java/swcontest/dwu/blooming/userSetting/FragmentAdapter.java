package swcontest.dwu.blooming.userSetting;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {
    public int mCount;

    public FragmentAdapter(FragmentActivity fa, int count){
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index == 0) return new UserNameActivity();
        else if(index == 1) return new UserBirthActivity();
        else if(index == 2) return new UserHomeActivity();
        else if(index == 3) return new UserPeriodActivity();
        else if(index == 4) return new UserSleepActivity();
        else return new UserPhoneActivity();
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public int getRealPosition(int position){
        return position % mCount;
    }
}
