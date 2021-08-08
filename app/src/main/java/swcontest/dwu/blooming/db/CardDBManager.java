package swcontest.dwu.blooming.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import swcontest.dwu.blooming.dto.CardDto;

public class CardDBManager {

    CardDBHelper helper = null;
    Cursor cursor = null;

    public CardDBManager(Context context){
        helper = new CardDBHelper(context);
    }

    public ArrayList<CardDto> getAllCard(){
        ArrayList cardList = new ArrayList();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ helper.TABLE_NAME, null);

        while(cursor.moveToNext()){
            long id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(helper.COL_TITLE));
            String date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
            int price = cursor.getInt(cursor.getColumnIndex(helper.COL_PRICE));

            cardList.add(new CardDto(id,  title, date, price));
        }

        cursor.close();
        helper.close();
        return cardList;
    }

    public boolean addNewCard(CardDto newTravel){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(helper.COL_TITLE, newTravel.getTitle());
        value.put(helper.COL_DATE, newTravel.getDate());
        value.put(helper.COL_PRICE, newTravel.getPrice());


        long count = db.insert(helper.TABLE_NAME, null, value);
        helper.close();
        if(count > 0) return true;
        return false;
    }

    public boolean removeTravel(long id){
        SQLiteDatabase db = helper.getWritableDatabase();
        String whereClause = helper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        int result = db.delete(helper.TABLE_NAME, whereClause,whereArgs);
        helper.close();
        if (result > 0) return true;
        return false;
    }

    public boolean modifyTravel(CardDto travel){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(helper.COL_TITLE, travel.getTitle());
        value.put(helper.COL_DATE, travel.getDate());
        value.put(helper.COL_PRICE, travel.getPrice());

        String whereClause = helper.COL_ID+"=?";
        String[] whereArgs = new String[] {String.valueOf(travel.get_id())};

        int result = db.update(helper.TABLE_NAME, value, whereClause, whereArgs);

        helper.close();
        if (result > 0) return true;
        return false;
    }

    public void close() {
        if (helper != null) helper.close();
        if (cursor != null) cursor.close();
    };
}
