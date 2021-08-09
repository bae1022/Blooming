package swcontest.dwu.blooming.diary;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

import swcontest.dwu.blooming.R;

public class EventDecorator implements DayViewDecorator {

    private final Drawable drawable;
    private int color;
    private HashSet<CalendarDay> dates;
    private TextView textView;

    public EventDecorator(int color, Collection<CalendarDay> dates,Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.more);
        this.color = color;
        this.dates = new HashSet<>(dates);
        this.textView = textView;
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {

        view.setSelectionDrawable(drawable);
        //view.addSpan(new DotSpan(5, color)); // 날자밑에 점
    }
    public void setText(String text){
        textView.setText(text);
    }

}
