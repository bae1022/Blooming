package swcontest.dwu.blooming;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import swcontest.dwu.blooming.adapter.DailyMemoCursorAdapter;
import swcontest.dwu.blooming.db.DailyMemoDBHelper;
import swcontest.dwu.blooming.service.DailyMemoService;

public class DailyMemoResetBroadcastReceiver extends BroadcastReceiver {

    DailyMemoDBHelper helper;
    Cursor cursor;
    DailyMemoCursorAdapter adapter;

    @Override
    public void onReceive(Context context, Intent intent) {

        helper = new DailyMemoDBHelper(context.getApplicationContext());
        adapter = new DailyMemoCursorAdapter(context.getApplicationContext(), R.layout.dailymemo_item, null);

        SQLiteDatabase sdb = helper.getWritableDatabase();

        sdb.delete(helper.TABLE_NAME, null, null);

        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + helper.TABLE_NAME, null);

        adapter.changeCursor(cursor);
        helper.close();

        Toast.makeText(context, "ok", Toast.LENGTH_LONG).show();
        Log.d("resetAlarm", "완료");
    }
}
