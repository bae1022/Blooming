package swcontest.dwu.blooming.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import swcontest.dwu.blooming.dto.LocationDto;

public class LocationDBManager {

    LocationDBHelper dbHelper = null;
    Cursor cursor = null;

    public LocationDBManager(Context context) { dbHelper = new LocationDBHelper(context); }

    public ArrayList<LocationDto> getAllLocation() {
        ArrayList locationList = new ArrayList();
        SQLiteDatabase locationDB = dbHelper.getReadableDatabase();
        cursor = locationDB.rawQuery("SELECT * FROM " + LocationDBHelper.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(LocationDBHelper.COL_ID));
            String date = cursor.getString(cursor.getColumnIndex(LocationDBHelper.COL_DATE));
            String time = cursor.getString(cursor.getColumnIndex(LocationDBHelper.COL_TIME));
            float latitude = cursor.getFloat(cursor.getColumnIndex(LocationDBHelper.COL_LAT));
            float longitude = cursor.getFloat(cursor.getColumnIndex(LocationDBHelper.COL_LON));

            locationList.add(new LocationDto(id, date, time, latitude, longitude));
        }

        cursor.close();
        dbHelper.close();
        return locationList;
    }

    public boolean addLocation(LocationDto newLocation) {
        SQLiteDatabase locationDB = dbHelper.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(dbHelper.COL_DATE, newLocation.getDate());
        value.put(dbHelper.COL_TIME, newLocation.getTime());
        value.put(dbHelper.COL_LAT, newLocation.getLatitude());
        value.put(dbHelper.COL_LON, newLocation.getLongitude());

        long count = locationDB.insert(dbHelper.TABLE_NAME, null, value);
        dbHelper.close();

        if (count > 0) {
            return true;
        }
        return false;
    }

    public ArrayList<LocationDto> getLocation() {
        ArrayList locationList = new ArrayList();
        SQLiteDatabase locationDB = dbHelper.getReadableDatabase();

        cursor = locationDB.rawQuery("SELECT distinct date FROM " + LocationDBHelper.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(LocationDBHelper.COL_DATE));

            locationList.add(new LocationDto(date));
        }

        cursor.close();
        dbHelper.close();
        return locationList;
    }

    public ArrayList<LocationDto> getLocationByDate(String getDate) {
        ArrayList locationList = new ArrayList();
        SQLiteDatabase locationDB = dbHelper.getReadableDatabase();

        String[] columns = null;
        String selection = dbHelper.COL_DATE + "=?";
        String[] selectArgs = new String[] {getDate};

        cursor = locationDB.query(LocationDBHelper.TABLE_NAME, columns, selection, selectArgs,null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(LocationDBHelper.COL_ID));
            String date = cursor.getString(cursor.getColumnIndex(LocationDBHelper.COL_DATE));
            String time = cursor.getString(cursor.getColumnIndex(LocationDBHelper.COL_TIME));
            float latitude = cursor.getFloat(cursor.getColumnIndex(LocationDBHelper.COL_LAT));
            float longitude = cursor.getFloat(cursor.getColumnIndex(LocationDBHelper.COL_LON));

            locationList.add(new LocationDto(id, date, time, latitude, longitude));
        }

        cursor.close();
        dbHelper.close();
        return locationList;
    }

    public boolean removeLocation(long id) {
        SQLiteDatabase locationDB = dbHelper.getWritableDatabase();
        String whereClause = dbHelper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        int result = locationDB.delete(dbHelper.TABLE_NAME, whereClause, whereArgs);
        dbHelper.close();

        if (result > 0) {
            return true;
        }
        return false;
    }
}
