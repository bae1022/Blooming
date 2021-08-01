package swcontest.dwu.blooming.userSetting;

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
        tv_title = itemView.findViewById(R.id.tv_name);
        c_layout = itemView.findViewById(R.id.c_layout);
    }

    public void onBind(UserData data){
        this.data = data;
        tv_title.setText(data.getTitle());
        c_layout.setBackgroundResource(data.getColor());
    }
}
