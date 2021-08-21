package swcontest.dwu.blooming.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CardDBHelper extends SQLiteOpenHelper {
    final static String TAG="CardDBHelper";

    final static String DB_NAME = "card_table";

    public final static String TABLE_NAME = "MyCard_table";

    public final static String COL_ID = "_id";
    public final static String COL_TITLE = "title";
    public final static String COL_PRICE = "price";
    public final static String COL_DATE = "date";

    public CardDBHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME +" ("+ COL_ID +" integer primary key autoincrement, " +
                COL_TITLE + " TEXT," + COL_DATE + " TEXT," + COL_PRICE + " TEXT)";

        Log.d(TAG, sql);
        db.execSQL(sql);

        db.execSQL("insert into " + TABLE_NAME + " values (null, '하나로마트', '2021-08-07 21:33', '5000원');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
