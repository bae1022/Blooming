package swcontest.dwu.blooming.userSetting;

import android.content.Context;
import android.util.Log;
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
        Log.d("ViewPagerAdapter", "1");
        this.listData = data;
    }

    //RecyclerView가 ViewHolder항목을 나타내기 위해 지정된 유형의 새 항목이 필요할경우
    @Override
    public ViewHolderPage onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("ViewPagerAdapter", "onCreateViewHolder");
        Context context = parent.getContext();
        Log.d("ViewPagerAdapter", "viewType: " + viewType);
        //기존
        View view = LayoutInflater.from(context).inflate(R.layout.setting_name, parent, false);
        return new ViewHolderPage(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderPage holder, int position) {
        Log.d("ViewPagerAdapter", "onBindViewHolder, int position= " + position);
        if(holder instanceof ViewHolderPage){
            ViewHolderPage viewHolder = (ViewHolderPage) holder;
            viewHolder.onBind(listData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        Log.d("ViewPagerAdapter", "getItemCount" );
        return listData.size();
    }
}
