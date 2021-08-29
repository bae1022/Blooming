package swcontest.dwu.blooming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import swcontest.dwu.blooming.R;
import swcontest.dwu.blooming.dto.LocationDto;

public class LocationAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<LocationDto> list;
    private LayoutInflater inflater;

    public LocationAdapter(Context context, int layout, ArrayList<LocationDto> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LocationAdapter.ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(layout, parent, false);
            viewHolder = new LocationAdapter.ViewHolder();
            viewHolder.tvDateLocation = view.findViewById(R.id.tvDateLocation);
            view.setTag(viewHolder);
        } else {
            viewHolder = (LocationAdapter.ViewHolder)view.getTag();
        }

        LocationDto dto = list.get(position);

        viewHolder.tvDateLocation.setText(dto.getDate());

        return view;
    }

    static class ViewHolder {
        TextView tvDateLocation;
    }
}
