package swcontest.dwu.blooming.diary;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import swcontest.dwu.blooming.R;

public class MyGridAdapter extends ArrayAdapter {
    List<Date> dates;
    Calendar currentDate;
    List<Events> events;
    LayoutInflater inflater;
    Context mContext;

    public MyGridAdapter(@NonNull Context context, List<Date> dates, Calendar currentDate, List<Events> events ) {
        super(context, R.layout.single_cell_layout);
        this.dates = dates;
        this.currentDate = currentDate;
        this.events = events;
        inflater = LayoutInflater.from(context);
        mContext = context;

    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);


        int DayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH) +1;
        int displayYear = dateCalendar.get(Calendar.YEAR);

        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        int currentYear = currentDate.get(Calendar.YEAR);
        //int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);


        View view = convertView;

        if(view == null){
            view = inflater.inflate(R.layout.single_cell_layout, parent,false);
        }

        if(displayMonth == currentMonth && displayYear == currentYear){
            view.setBackgroundColor(getContext().getResources().getColor(R.color.blue1));
        }
        else {
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }



        TextView Day_Number = view.findViewById(R.id.calendar_day);
        TextView EventNumber = view.findViewById(R.id.events_id);
        //Log.d("Day_Number",));

        Day_Number.setText(String.valueOf(DayNo));
        Calendar eventCalendar = Calendar.getInstance();

        //일기 표시
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 0; i < events.size(); i++){
            eventCalendar.setTime(ConvertStringToDate( events.get(i).getDATE()));
            if(DayNo == eventCalendar.get(Calendar.DAY_OF_MONTH)
                    && displayMonth == eventCalendar.get(Calendar.MONTH)+1
                    && displayYear == eventCalendar.get(Calendar.YEAR) ){
                arrayList.add(events.get(i).getEVENTS());
                EventNumber.setText(arrayList.size() + " Events");

            }
        }

        //오늘 day 가져옴
        Calendar mCal = Calendar.getInstance();
        int today = mCal.get(Calendar.DAY_OF_MONTH);
        int month = mCal.get(Calendar.MONTH) +1;
        int year = mCal.get(Calendar.YEAR);

        Log.d("today", today + "일 " + month + "월" + year + "년");
        //오늘날짜 표시

        if(month == currentMonth && year == currentYear && today == DayNo){
            Day_Number.setTextColor(mContext.getResources().getColor(R.color.colorToday));
        }
        Day_Number.setVisibility(View.VISIBLE);



        return view;
    }

    private Date ConvertStringToDate(String eventDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date date = null;
        try{
            date = format.parse(eventDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

}
