package swcontest.dwu.blooming.userSetting;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import swcontest.dwu.blooming.R;

public class ViewHolderPage extends RecyclerView.ViewHolder{
    private TextView tv_title;
    private ConstraintLayout c_layout;
    UserData data;

    ViewHolderPage(View itemView){
        super(itemView);
        Log.d("ViewHolderPage", "ViewHolderPage " );
        //기존
        tv_title = itemView.findViewById(R.id.tv_name);
        c_layout = itemView.findViewById(R.id.c_layout_1);
        //수정
//        if(data.getColor() == 1) {
//            tv_title = itemView.findViewById(R.id.tv_name);
//            c_layout = itemView.findViewById(R.id.c_layout_1);
//        } else
//            c_layout = itemView.findViewById(R.id.c_layout_2);
    }

    public void onBind(UserData data){
        Log.d("ViewHolderPage", "onBind" );
        this.data = data;
//        tv_title.setText(data.getTitle());
        c_layout = itemView.findViewById(R.id.c_layout_2);
//        c_layout.setBackgroundResource(data.getColor());
    }
}
