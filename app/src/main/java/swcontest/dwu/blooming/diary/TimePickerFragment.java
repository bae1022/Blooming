package swcontest.dwu.blooming.diary;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

// implements  TimePickerDialog.OnTimeSetListener
public class TimePickerFragment extends DialogFragment {
   private AlarmManager mAlarmManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mAlarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

//        return new TimePickerDialog(getContext(), this, hour, minute,
//                DateFormat.is24HourFormat(getContext()));
        return  new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener)getActivity(), hour,
                minute, DateFormat.is24HourFormat(getActivity()));
    }

//    @Override
//    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        calendar.set(Calendar.MINUTE, minute);
//
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        PendingIntent operation = PendingIntent.getActivity(getContext(), 0
//        , intent, 0);
//
//        mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), operation);
//
//    }
}
