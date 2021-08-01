package swcontest.dwu.blooming.userSetting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import swcontest.dwu.blooming.R;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewHolderPage>{
    private ArrayList<UserData> listData;

    ViewPagerAdapter(ArrayList<UserData> data){
        this.listData = data;
    }

    @Override
    public ViewHolderPage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.setting_name, parent, false);
        return new ViewHolderPage(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderPage holder, int position) {
        if(holder instanceof ViewHolderPage){
            ViewHolderPage viewHolder = (ViewHolderPage) holder;
            viewHolder.onBind(listData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
