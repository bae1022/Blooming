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

//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 4월 12일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 5월 12일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 6월 12일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 7월 12일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 8월 12일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 9월 12일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 1월 12일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 2월 12일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 3월 12일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 2월 2일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 4월 22일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 4월 23일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 4월 24일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 4월 25일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 4월 26일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 4월 27일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 4월 28일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 5월 21일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 1월 2일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 1월 3일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 1월 4일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 1월 5일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 1월 6일', '2020년 4월 12일', null, null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 1월 7일', '2020년 4월 12일', null, null);");
   }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}