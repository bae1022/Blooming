package swcontest.dwu.blooming.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DiaryDBHelper extends SQLiteOpenHelper {
    final static String TAG="DiaryDBHelper";
    final static String DB_NAME = "diary.db";

    public final static String TABLE_NAME = "diary_table";

    public final static String COL_ID = "_id";
    public final static String COL_DATE = "date";
    public final static String COL_DIARY = "diary";


    public DiaryDBHelper(Context context){
        super(context, DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME +" ("+ COL_ID +" integer primary key autoincrement, " +
              COL_DATE + " TEXT," + COL_DIARY + " TEXT)";

        Log.d(TAG, sql);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
