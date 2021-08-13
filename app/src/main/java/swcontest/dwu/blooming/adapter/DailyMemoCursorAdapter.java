package swcontest.dwu.blooming.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

import swcontest.dwu.blooming.R;
import swcontest.dwu.blooming.db.DailyMemoDBHelper;

public class DailyMemoCursorAdapter extends CursorAdapter {

    LayoutInflater inflater;
    int layout;

    public DailyMemoCursorAdapter(Context context, int layout, Cursor c){
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = inflater.inflate(layout, viewGroup, false);

        ViewHolder holder = new ViewHolder();
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (holder.tvMemoTime == null){
            holder.tvMemoTime = view.findViewById(R.id.memo_list_time);
            holder.tvMemoContent = view.findViewById(R.id.memo_list_content);
        }

        holder.tvMemoTime.setText(cursor.getString(cursor.getColumnIndex(DailyMemoDBHelper.COL_TIME)));
        holder.tvMemoContent.setText(cursor.getString(cursor.getColumnIndex(DailyMemoDBHelper.COL_CONTENT)));
    }

    static class ViewHolder {
        public ViewHolder(){
            tvMemoTime = null;
            tvMemoContent = null;
        }

        TextView tvMemoTime;
        EditText tvMemoContent;
    }
}
