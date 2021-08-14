package swcontest.dwu.blooming.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import swcontest.dwu.blooming.dto.DiaryDto;

public class DiaryDBManager {
    DiaryDBHelper helper = null;
    Cursor cursor = null;

    public DiaryDBManager(Context context){
        helper = new DiaryDBHelper(context);
    }

    public ArrayList<DiaryDto> getDiary(){
        ArrayList diaryList = new ArrayList();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ helper.TABLE_NAME, null);

        while(cursor.moveToNext()){
            long _id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
            String date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
            String diary = cursor.getString(cursor.getColumnIndex(helper.COL_DIARY));

            diaryList.add(new DiaryDto(_id, date, diary));
        }

        cursor.close();
        helper.close();
        return diaryList;
    }

    public boolean addNewDiary(DiaryDto newDiary){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(helper.COL_DATE, newDiary.getDate());
        value.put(helper.COL_DIARY, newDiary.getDiary());

        long count = db.insert(helper.TABLE_NAME, null, value);
        helper.close();
        if(count > 0) return true;
        return false;
    }

}
