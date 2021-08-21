package swcontest.dwu.blooming;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import swcontest.dwu.blooming.dto.CardDto;

public class CardListAdapter extends BaseAdapter {

    public static final String TAG = "card adapter";

    private ArrayList<CardDto> list;
    private LayoutInflater inflater;
    private Context context;
    private int layout;

    public CardListAdapter(Context context, int resource, ArrayList<CardDto> list) {
        this.context = context;
        this.layout = resource;
        this.list = list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = view.findViewById(R.id.tvTitle);
            viewHolder.tvPrice = view.findViewById(R.id.tvPrice);
            viewHolder.tvDate = view.findViewById(R.id.tvDate);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        CardDto dto = list.get(position);
        Log.d(TAG, dto.getTitle());

        viewHolder.tvTitle.setText(dto.getTitle());
        viewHolder.tvPrice.setText(dto.getPrice());
        viewHolder.tvDate.setText(dto.getDate());

        return view;
    }

    static class ViewHolder {
        public TextView tvTitle = null;
        public TextView tvPrice = null;
        public TextView tvDate = null;
    }
}
