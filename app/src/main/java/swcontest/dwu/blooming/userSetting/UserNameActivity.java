package swcontest.dwu.blooming.userSetting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import swcontest.dwu.blooming.R;

public class UserNameActivity extends Fragment {
    private String title;
    private int page;

    public static UserNameActivity newInstance(int page, String title){
        UserNameActivity fragment = new UserNameActivity();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    //전달된 인수를 기반으로 인스턴스 변수 저장
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_name, container, false);
        TextView tv_question = view.findViewById(R.id.tv_name);
        tv_question.setText(page + "-----" + title);
        return view;
    }
}
