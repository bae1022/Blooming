package swcontest.dwu.blooming.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationDBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "location_db";
    public final static String TABLE_NAME = "location_table";
    public final static String COL_ID = "_id";
    public final static String COL_LAT = "latitude";
    public final static String COL_LON = "longitude";
    public final static String COL_DATE = "date";
    public final static String COL_TIME = "time";

    public LocationDBHelper(Context context) { super(context, DB_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + COL_ID + " integer primary key autoincrement,"
                + COL_DATE + " TEXT, " + COL_TIME + " TEXT, " + COL_LAT + " FLOAT, "
                + COL_LON + " FLOAT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}