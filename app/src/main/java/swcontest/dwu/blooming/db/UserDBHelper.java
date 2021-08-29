package swcontest.dwu.blooming.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

public class UserDBHelper extends SQLiteOpenHelper {
    final static String DB_NAME = "user.db";
    public final static String TABLE_NAME = "user_table";
    public final static String COL_ID = "_id";
    public final static String COL_NAME = "name";
    public final static String COL_MONTH = "month";
    public final static String COL_DAY = "day";
    public final static String COL_YEAR = "year";
    public final static String COL_ADDRESS = "address";
    public final static String COL_PERIOD = "period";
    public final static String COL_WAKE = "wake";
    public final static String COL_SLEEP = "sleep";
    public final static String COL_PHONE = "phone";

    public UserDBHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
                    COL_NAME + " TEXT, " + COL_YEAR + " INTEGER, " + COL_MONTH + " INTEGER, " + COL_DAY + " INTEGER, " +
                COL_ADDRESS + " TEXT, " + COL_PERIOD + " INTEGER, " + COL_WAKE + " TEXT, " + COL_SLEEP + " TEXT, " + COL_PHONE + " TEXT)";
                Log.d("UserDBHelper", sql);
                db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
